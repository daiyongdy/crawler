package com.crawler.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.crawler.dao.mapper.biz.JumpAroundBizMapper;
import com.crawler.dao.mapper.db.JumpAroundMapper;
import com.crawler.dao.mapper.db.JumpParticipantMapper;
import com.crawler.dao.model.db.JumpAround;
import com.crawler.dao.model.db.JumpParticipant;
import com.crawler.dao.model.db.JumpParticipantExample;
import com.crawler.exception.BizException;
import com.crawler.model.WebUserDTO;
import com.crawler.model.WebUserHolder;
import com.crawler.util.HttpClientUtil;
import com.crawler.util.SignUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

	@Value("${api.user.balance.deduct}")
	private String API_USER_BALANCE_DEDUCT;

	@Value("${itobox.secret}")
	private String ITOBOX_SECRET;

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
	 * 获取未完成回合
	 * @param aroundId
	 * @param userId
	 * @return
	 */
	public JumpParticipant getParticipant(String aroundId, String userId) {
		JumpParticipantExample example = new JumpParticipantExample();
		JumpParticipantExample.Criteria criteria = example.createCriteria();
		criteria.andAroundIdEqualTo(aroundId);
		criteria.andUserIdEqualTo(userId);
		List<JumpParticipant> jumpParticipants = participantMapper.selectByExample(example);
		return CollectionUtils.isEmpty(jumpParticipants) ? null : jumpParticipants.get(0);
	}

	/**
	 * 客户完成游戏 加积分
	 *
	 * @param aroundId
	 * @param score
	 * @return
	 */
	public boolean score(String aroundId, int score) {

		WebUserDTO user = WebUserHolder.getUser();

		JumpParticipant participant;
		JumpParticipantExample example = new JumpParticipantExample();
		JumpParticipantExample.Criteria criteria = example.createCriteria();
		criteria.andUserIdEqualTo(user.getUserId());
		criteria.andAroundIdEqualTo(aroundId);
		criteria.andIsOverEqualTo(false);
		List<JumpParticipant> jumpParticipants = participantMapper.selectByExample(example);

		if (!CollectionUtils.isEmpty(jumpParticipants)) {
			participant = jumpParticipants.get(0);

			LOG.info("上报用户得分, userId:{}, userName:{}, aroundId:{}, score:{}",
					user.getUserId(), user.getUserName(), participant.getAroundId(), score);

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
				around.setStatus(2);
				around.setUpdateTime(new Date());
				aroundMapper.updateByPrimaryKeySelective(around);

				//排序 做排名
				Collections.sort(aroundParticipants, new PointCompator());
				for (int i = 0; i < aroundParticipants.size(); i++) {
					JumpParticipant participant1 = aroundParticipants.get(i);
					participant1.setRankNum(i + 1);
				}
				if (aroundParticipants.size() <= 10) {
					JumpParticipant participant1 = aroundParticipants.get(0);
					participant1.setIsWin(true);
				} else {
					int winNum = (int) (aroundParticipants.size() * 0.2);

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
	public List<JumpParticipant> getParticipantsOrderByTime(String aroundId) {
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

//		boolean inAround = isInAround(aroundId, user.getUserId());
		JumpParticipant participant2 = getParticipant(aroundId, user.getUserId());
		if (participant2 != null) {
			if (participant2.getIsOver() == true) {
				throw BizException.AROUND_IS_OVER;
			} else {
				return true;
			}
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

		Map<String, Object> params = Maps.newHashMap();
		params.put("userId", user.getUserId());
		params.put("aid", around.getAroundId());
		params.put("amount", around.getMoney().toString());
		params.put("timestamp", String.valueOf(System.currentTimeMillis()));
		params.put("sign", SignUtils.sign(params, ITOBOX_SECRET));
		try {
			String result = HttpClientUtil.httpGetRequest(API_USER_BALANCE_DEDUCT, params, 10000, 10000);
			LOG.info("参与回合扣减用户余额, userName:{} params:{}, result:{}", user.getUserName(), JSON.toJSONString(params), result);

			JSONObject resultObject = JSON.parseObject(result);
			if (resultObject.getIntValue("RET") == 1) {
				LOG.info("参与回合扣减用户余额成功, userName:{}, params:{}", user.getUserName(), JSON.toJSONString(params));
			} else {
				LOG.error("参与回合调用itobox扣减余额接口失败, userName:{}, params:{}", user.getUserName(), JSON.toJSONString(params));
				throw BizException.DEDUCT_BALANCE_FAIL;
			}
		} catch (URISyntaxException e) {
			LOG.error("参与回合调用itobox扣减余额接口异常, userName:{}, params:{}", user.getUserName(), JSON.toJSONString(params), e);
			throw BizException.DEDUCT_BALANCE_FAIL;
		}

		//修改
		around.setCurrentParticipantsNum(around.getCurrentParticipantsNum() + 1);
		around.setTotalAmout(around.getTotalAmout().add(around.getMoney()));
		around.setUpdateTime(new Date());
		aroundMapper.updateByPrimaryKeySelective(around);

		LOG.info("用户名称:{}, 用户id:{} 参加了 回合:{} 成功, 回合名称:{}", user.getUserName(), user.getUserId(), around.getAroundId(), around.getAroundName());

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

	/**c
	 * 记录游戏开始时间
	 * @return
	 * @param aroundId
	 */
	public boolean gameStart(String aroundId) {
		WebUserDTO user = WebUserHolder.getUser();

		JumpParticipant participant;
		JumpParticipantExample example = new JumpParticipantExample();
		JumpParticipantExample.Criteria criteria = example.createCriteria();
		criteria.andUserIdEqualTo(user.getUserId());
		criteria.andAroundIdEqualTo(aroundId);
		criteria.andIsOverEqualTo(false);
		List<JumpParticipant> jumpParticipants = participantMapper.selectByExample(example);

		if (!CollectionUtils.isEmpty(jumpParticipants)) {
			participant = jumpParticipants.get(0);

			LOG.info("游戏开始, userId:{}, userName:{}, aroundId:{}",
					user.getUserId(), user.getUserName(), participant.getAroundId());

			//游戏已经结束
			if (participant.getIsOver()) {
				LOG.info("此用户此回合游戏已经结束, userId:{}, aroundId:{}, participantId:{}",
						user.getUserId(), participant.getAroundId(), participant.getParticipantId());
				throw BizException.AROUND_IS_OVER;
			}
			participant.setStartTime(new Date());
			participantMapper.updateByPrimaryKeySelective(participant);
		} else {
			LOG.info("此用户不存在未结束回合游戏, userId:{}",
					user.getUserId());
			throw BizException.USER_AROUND_NOT_EXISTS;
		}

		return true;
	}

	class PointCompator implements Comparator<JumpParticipant> {
		@Override
		public int compare(JumpParticipant o1, JumpParticipant o2) {
			if ((o2.getPoint() - o1.getPoint()) != 0) {
				return o2.getPoint() - o1.getPoint();
			} else {
				int time1 = Long.valueOf(o1.getUpdateTime().getTime() - (o1.getStartTime() != null ? o1.getStartTime().getTime() : o1.getParticipantTime().getTime())).intValue();
				int time2 = Long.valueOf(o2.getUpdateTime().getTime() - (o2.getStartTime() != null ? o2.getStartTime().getTime() : o2.getParticipantTime().getTime())).intValue();
				return time1 - time2;
			}
		}
	}

	public static void main(String[] args) throws ParseException {
//		double x = 3.8;
//		System.out.println((int)x);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		List<JumpParticipant> participants = Lists.newArrayList();
		JumpParticipant p1 = new JumpParticipant();
		p1.setPoint(1);
		p1.setParticipantTime(sdf.parse("2018-08-22 12:30:30"));
		p1.setUpdateTime(sdf.parse("2018-08-22 13:30:35"));
		p1.setParticipantName("p1");
		JumpParticipant p2 = new JumpParticipant();
		p2.setPoint(1);
		p2.setParticipantTime(sdf.parse("2018-08-22 12:30:30"));
		p2.setUpdateTime(sdf.parse("2018-08-22 13:30:31"));
		p2.setParticipantName("p2");
		JumpParticipant p3 = new JumpParticipant();
		p3.setPoint(1);
		p3.setParticipantTime(sdf.parse("2018-08-22 12:30:30"));
		p3.setUpdateTime(sdf.parse("2018-08-22 13:30:32"));
		p3.setParticipantName("p3");
		participants.add(p1);
		participants.add(p2);
		participants.add(p3);
//		Collections.sort(participants, new PointCompator());

		System.out.println(JSON.toJSONString(participants));
	}
}
