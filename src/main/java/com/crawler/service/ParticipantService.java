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

import java.util.Date;
import java.util.List;

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

	public boolean isInAround(String aroundId, String userId) {
		JumpParticipantExample example = new JumpParticipantExample();
		JumpParticipantExample.Criteria criteria = example.createCriteria();
		criteria.andAroundIdEqualTo(aroundId);
		criteria.andUserIdEqualTo(userId);
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

		JumpParticipant participant = null;

		JumpParticipantExample example = new JumpParticipantExample();
		JumpParticipantExample.Criteria criteria = example.createCriteria();
		criteria.andUserIdEqualTo(user.getUserId());
		criteria.andIsOverEqualTo(false);
		List<JumpParticipant> jumpParticipants = participantMapper.selectByExample(example);
		if (!CollectionUtils.isEmpty(jumpParticipants)) {
			participant = jumpParticipants.get(0);
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
			LOG.info("此用户不存在未结束回合游戏, userId:{}, aroundId:{}, participantId:{}, score:{}",
					user.getUserId(), participant.getAroundId(), participant.getParticipantId(), score);
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
				}
			}
			//回合结束
			if (isOver) {
				around.setStatus(3);
				around.setUpdateTime(new Date());
				aroundMapper.updateByPrimaryKeySelective(around);
			}
		}

		return true;
	}
}
