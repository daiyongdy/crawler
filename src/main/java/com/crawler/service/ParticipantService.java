package com.crawler.service;

import com.crawler.dao.mapper.biz.JumpAroundBizMapper;
import com.crawler.dao.mapper.db.JumpAroundMapper;
import com.crawler.dao.mapper.db.JumpParticipantMapper;
import com.crawler.dao.model.db.JumpAround;
import com.crawler.dao.model.db.JumpParticipant;
import com.crawler.dao.model.db.JumpParticipantExample;
import com.crawler.exception.BizException;
import com.crawler.model.WebUserDTO;
import com.crawler.model.WebUserHolder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * Created by daiyong on 2018/8/12.
 * email daiyong@qiyi.com
 */
@Service
@Transactional
public class ParticipantService {

	private static final Logger LOG = LogManager.getLogger("sysLog");

	@Autowired
	private JumpParticipantMapper participantMapper;

	@Autowired
	private JumpAroundBizMapper aroundBizMapper;

	@Autowired
	private JumpAroundMapper aroundMapper;

	/**
	 * 判断是否在此回合中
	 * @param aroundId
	 * @param userId
	 * @return
	 */
	public boolean isInAround(String aroundId, String userId) {
		JumpParticipantExample example = new JumpParticipantExample();
		JumpParticipantExample.Criteria criteria = example.createCriteria();
		criteria.andAroundIdEqualTo(aroundId);
		criteria.andUserIdEqualTo(userId);
		List<JumpParticipant> jumpParticipants = participantMapper.selectByExample(example);
		return !CollectionUtils.isEmpty(jumpParticipants);
	}

	/**
	 * 判断是否有未完成回合
	 * @param userId
	 * @return
	 */
	public boolean hasUnFinishedAround(String userId) {
		JumpParticipantExample example = new JumpParticipantExample();
		JumpParticipantExample.Criteria criteria = example.createCriteria();
		criteria.andUserIdEqualTo(userId);
		criteria.andIsOverEqualTo(false);
		List<JumpParticipant> jumpParticipants = participantMapper.selectByExample(example);
		return !CollectionUtils.isEmpty(jumpParticipants);
	}

	/**
	 * 获取未完成回合
	 * @param userId
	 * @return
	 */
	public String getUnOverAroundId(String userId) {
		JumpParticipantExample example = new JumpParticipantExample();
		JumpParticipantExample.Criteria criteria = example.createCriteria();
		criteria.andUserIdEqualTo(userId);
		criteria.andIsOverEqualTo(false);
		List<JumpParticipant> jumpParticipants = participantMapper.selectByExample(example);
		if (!CollectionUtils.isEmpty(jumpParticipants)) {
			return jumpParticipants.get(0).getAroundId();
		} else {
			return null;
		}
	}


	/**
	 * 客户完成游戏 加积分
	 * @param score
	 * @return
	 */
	public boolean score(int score) {

		WebUserDTO user = WebUserHolder.getUser();

		JumpParticipant participant;
		JumpParticipantExample example = new JumpParticipantExample();
		JumpParticipantExample.Criteria criteria = example.createCriteria();
		criteria.andUserIdEqualTo(user.getUserId());
		criteria.andIsOverEqualTo(false);
		List<JumpParticipant> jumpParticipants = participantMapper.selectByExample(example);
		if (!CollectionUtils.isEmpty(jumpParticipants)) {
			participant = jumpParticipants.get(0);

			LOG.info("上报用户得分, userId:{}, userName:{}, aroundId:{}",
					user.getUserId(), user.getUserName(), participant.getAroundId());

			//游戏已经结束
			if (participant.getIsOver()) {
				LOG.info("此用户此回合游戏已经结束, userId:{}, aroundId:{}, participantId:{}, score:{}",
						user.getUserId(), participant.getAroundId(), participant.getParticipantId(), score);
				throw BizException.AROUND_IS_OVER;
			}
			participant.setPoint(score);
			participant.setIsOver(true);
			participant.setUpdateTime(new Date());
			participantMapper.updateByPrimaryKeySelective(participant);
		} else {
			LOG.info("此用户不存在未结束回合游戏, userId:{}, score:{}",
					user.getUserId(), score);
			throw BizException.USER_AROUND_NOT_EXISTS;
		}

		//计算回合是否结束 必须首先锁定回合
		JumpAround around = aroundBizMapper.lockGet(participant.getAroundId());
		JumpParticipantExample example2 = new JumpParticipantExample();
		JumpParticipantExample.Criteria criteria2 = example2.createCriteria();
		criteria2.andAroundIdEqualTo(around.getAroundId());
		List<JumpParticipant> aroundParticipants = participantMapper.selectByExample(example2);
		if (aroundParticipants.size() < around.getMinParticipantsNum()) {
			LOG.info("参与者数量小于最小要求数量, userId:{}, aroundId:{}, participantId:{}, score:{}",
					user.getUserId(), around.getAroundId(), participant.getParticipantId(), score);
		} else {
			boolean isOver = true;
			for (JumpParticipant aroundParticipant : aroundParticipants) {
				if (aroundParticipant.getParticipantId() != participant.getParticipantId()) {
					if (!aroundParticipant.getIsOver()) {
						isOver = false;
						break;
					}
				} else {
					aroundParticipant.setPoint(score);
					aroundParticipant.setIsOver(true);
					aroundParticipant.setUpdateTime(new Date());
				}
			}
			//回合结束
			if (isOver) {
				around.setStatus(3);
				around.setUpdateTime(new Date());
				aroundMapper.updateByPrimaryKeySelective(around);

				
				//FDY 2018/8/13 下午1:49 回合结束操作
				//排序 做排名
				Collections.sort(aroundParticipants, new PointCompator());
				for (int i = 0; i < aroundParticipants.size(); i++) {
					JumpParticipant participant1 = aroundParticipants.get(i);
					participant1.setRankNum(i + 1);
				}
				if (aroundParticipants.size() <= 20) {
					JumpParticipant participant1 = aroundParticipants.get(0);
					participant1.setIsWin(true);
				} else {
					int winNum = (int) (aroundParticipants.size() * 0.1);
					for (int i = 0; i < aroundParticipants.size(); i++) {
						if ((i + 1) <= winNum) {
							JumpParticipant participant1 = aroundParticipants.get(i);
							participant1.setIsWin(true);
						} else {
							break;
						}
					}
				}
				for (JumpParticipant aroundParticipant : aroundParticipants) {
					participantMapper.updateByPrimaryKeySelective(aroundParticipant);
				}

			}
		}

		return true;
	}

	/**
	 * 获取所有参与者
	 * @param aroundId
	 * @return
	 */
	public List<JumpParticipant> getParticipants(String aroundId) {
		JumpParticipantExample example = new JumpParticipantExample();
		JumpParticipantExample.Criteria criteria = example.createCriteria();
		criteria.andAroundIdEqualTo(aroundId);
		example.setOrderByClause("participant_time asc");
		List<JumpParticipant> jumpParticipants = participantMapper.selectByExample(example);
		return jumpParticipants;
	}

	/**
	 * 参与游戏
	 * @param aroundId
	 * @return
	 */
	public boolean join(String aroundId) {

		JumpAround around = aroundBizMapper.lockGet(aroundId);
		if (null == around) {
			throw BizException.AROUND_NOT_EXISTS;
		}

		if (around.getStatus() == 2 || around.getStatus() == 3) {
			throw BizException.AROUND_IS_OVER;
		}


		WebUserDTO user = WebUserHolder.getUser();

		boolean inAround = isInAround(aroundId, user.getUserId());
		if (inAround) {
			throw BizException.USER_IS_IN_AROUND;
		}

		if (hasUnFinishedAround(user.getUserId())) {
			throw BizException.HAS_UNFINISHED_AROUND;
		}

		if (user.getBalance().compareTo(around.getMoney()) < 0) {
			throw BizException.BALANCE_NOT_ENOUGH;
		}

		//添加回合参与人
		JumpParticipant participant = new JumpParticipant();
		participant.setUserId(user.getUserId());
		participant.setParticipantName(user.getUserName());
		participant.setAroundId(around.getAroundId());
		participant.setMoney(around.getMoney());
		participant.setPoint(-1);
		participant.setRankNum(0);
		participant.setIsWin(false);
		participant.setIsOver(false);
		participant.setParticipantTime(new Date());
		participant.setUpdateTime(null);
		participantMapper.insertSelective(participant);

		//FDY 2018/8/13 上午11:13 扣减余额

		//修改
		around.setCurrentParticipantsNum(around.getCurrentParticipantsNum() + 1);
		around.setTotalAmout(around.getTotalAmout().add(around.getMoney()));
		around.setUpdateTime(new Date());
		aroundMapper.updateByPrimaryKeySelective(around);

		LOG.info("用户名称:{}, 用户id:{} 参加了 回合:{} 成功, 回合名称:{}", user.getUserName(), user.getUserId(), around.getMoney(), around.getAroundName());

		return true;
	}

	public List<JumpParticipant> getParticipantsOrderByRank(String aroundId) {
		JumpParticipantExample example = new JumpParticipantExample();
		JumpParticipantExample.Criteria criteria = example.createCriteria();
		criteria.andAroundIdEqualTo(aroundId);
		example.setOrderByClause("rank_num asc");
		List<JumpParticipant> jumpParticipants = participantMapper.selectByExample(example);
		return jumpParticipants;
	}

	class PointCompator implements Comparator<JumpParticipant> {

		@Override
		public int compare(JumpParticipant o1, JumpParticipant o2) {
			return o2.getPoint() - o1.getPoint();
		}
	}

	public static void main(String[] args) {
		double x = 3.8;
		System.out.println((int)x);
	}
}
