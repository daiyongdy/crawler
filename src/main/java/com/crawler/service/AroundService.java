package com.crawler.service;

import com.alibaba.fastjson.JSON;
import com.crawler.dao.mapper.biz.JumpAroundBizMapper;
import com.crawler.dao.mapper.db.JumpAroundMapper;
import com.crawler.dao.mapper.db.JumpParticipantMapper;
import com.crawler.dao.model.db.JumpAround;
import com.crawler.dao.model.db.JumpAroundExample;
import com.crawler.dao.model.db.JumpParticipant;
import com.crawler.exception.BizException;
import com.crawler.model.AroundInfoDTO;
import com.crawler.model.CheckDTO;
import com.crawler.model.WebUserDTO;
import com.crawler.model.WebUserHolder;
import com.crawler.util.DeviceUtil;
import com.crawler.util.UUIDGenerator;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by daiyong on 2018/8/11.
 * email daiyong@qiyi.com
 */
@Service
@Transactional
public class AroundService {

	private static final Logger LOG = LogManager.getLogger("sysLog");

	@Autowired
	private JumpAroundMapper aroundMapper;

	@Autowired
	private JumpAroundBizMapper aroundBizMapper;

	@Autowired
	private JumpParticipantMapper participantMapper;

	@Value("${api.user.balance.deduct}")
	private String API_USER_BALANCE_DEDUCT;

	@Autowired
	private ParticipantService participantService;


	/**
	 * 创建回合
	 * @param name
	 * @param minParticipant
	 * @param minMoney
	 */
	public void saveAround(String name, int minParticipant, String minMoney) {

		WebUserDTO user = WebUserHolder.getUser();

		BigDecimal minMoneyDecimal = new BigDecimal(minMoney);
		if (minMoneyDecimal.compareTo(new BigDecimal("0.001")) < 0 || minMoneyDecimal.compareTo(new BigDecimal("1")) > 0) {
			throw BizException.MIN_MONEY_EXEED;
		}
		BigDecimal balance = user.getBalance();
		if (balance.compareTo(minMoneyDecimal) < 0) {
			LOG.info("创建回合失败, 用户余额不足, userId:{}, userName:{}, userBalance:{}, aroundName:{}, aroundMinMoney:{}",
										user.getUserId(), user.getUserName(), user.getBalance(), name, minMoney);
			throw BizException.BALANCE_NOT_ENOUGH;
		}

		boolean nameExists = isNameExists(name);
		if (nameExists) {
			throw BizException.NAME_EXISTS;
		}

		//创建回合
		JumpAround around = new JumpAround();
		around.setAroundId(UUIDGenerator.random32UUID());
		around.setAroundName(name);
		around.setMinParticipantsNum(minParticipant);
		around.setCurrentParticipantsNum(1);
		around.setMoney(minMoneyDecimal);
		around.setTotalAmout(minMoneyDecimal);
		around.setStatus(1); //状态： 0：新建   1：进行中 2： 已结束 3：已结算
		around.setCreaterId(user.getUserId());
		around.setCreaterName(user.getUserName());
		around.setCreateTime(new Date());
		around.setUpdateTime(null);
		aroundMapper.insertSelective(around);

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

		//FDY 2018/8/11 下午4:48 扣减余额
		LOG.info("创建回合成功, userId:{}, userName:{}, around:{}", user.getUserId(), user.getUserName(), JSON.toJSONString(around));

	}

	private boolean isNameExists(String name) {
		JumpAroundExample example = new JumpAroundExample();
		JumpAroundExample.Criteria criteria = example.createCriteria();
		criteria.andAroundNameEqualTo(name);
		List<JumpAround> jumpArounds = aroundMapper.selectByExample(example);
		return !CollectionUtils.isEmpty(jumpArounds);
	}

	/**
	 * 检查各种信息
	 * @param request
	 * @param aroundId
	 * @return
	 */
	public CheckDTO check(HttpServletRequest request, String aroundId) {

		WebUserDTO user = WebUserHolder.getUser();

		CheckDTO checkDTO = new CheckDTO();

		boolean isFromMobile = DeviceUtil.check(request);
		checkDTO.setIsFromMobile(isFromMobile);

		//点击分享的连接
		if (StringUtils.isNotBlank(aroundId)) {
			JumpAround around = lockGet(aroundId);
			if (null == around) {
				throw BizException.AROUND_NOT_EXISTS;
			}
			checkDTO.setAroundId(aroundId);
			int status = around.getStatus();
			if (status == 2 || status == 3) {
				checkDTO.setIsAroundOver(true);
			} else {
				checkDTO.setIsAroundOver(false);
			}
			checkDTO.setAroundId(aroundId);
			checkDTO.setIsSelfCreate(around.getCreaterId().equals(user.getUserId()));
			checkDTO.setHasBalance(user.getBalance().compareTo(BigDecimal.ZERO) > 0);
			checkDTO.setIsBalanceEnough(user.getBalance().compareTo(around.getMoney()) > 0);
			checkDTO.setIsInAround(participantService.isInAround(around.getAroundId(), user.getUserId()));
		}
		//点击入口
		else {
			String unOverAroundId = participantService.getUnOverAroundId(user.getUserId());
			if (StringUtils.isNotBlank(unOverAroundId)) {
				JumpAround around = lockGet(unOverAroundId);
				checkDTO.setAroundId(aroundId);
				int status = around.getStatus();
				if (status == 3 || status == 4) {
					checkDTO.setIsAroundOver(true);
				} else {
					checkDTO.setIsAroundOver(false);
				}
				checkDTO.setAroundId(unOverAroundId);
				checkDTO.setIsSelfCreate(around.getCreaterId().equals(user.getUserId()));
				checkDTO.setHasBalance(user.getBalance().compareTo(BigDecimal.ZERO) > 0);
				checkDTO.setIsBalanceEnough(user.getBalance().compareTo(around.getMoney()) > 0);
				checkDTO.setIsInAround(true);
			}
			else {
				checkDTO.setIsInAround(false);
				checkDTO.setHasBalance(user.getBalance().compareTo(BigDecimal.ZERO) > 0);
			}
		}

		return checkDTO;
	}

	public JumpAround lockGet(String aroundId) {
		return aroundBizMapper.lockGet(aroundId);
	}

	/**
	 * 获取回合信息
	 * @param aroundId
	 * @return
	 */
	public AroundInfoDTO getAroundInfo(String aroundId) {
		JumpAround around = lockGet(aroundId);
		if (null == around) {
			throw BizException.AROUND_NOT_EXISTS;
		}

		WebUserDTO user = WebUserHolder.getUser();

		AroundInfoDTO aroundInfoDTO = new AroundInfoDTO();
		aroundInfoDTO.setCreaterName(around.getCreaterName());
		aroundInfoDTO.setMinParticipantsNum(around.getMinParticipantsNum());
		aroundInfoDTO.setMoney(around.getMoney());
		aroundInfoDTO.setCurrentParticipantsNum(around.getCurrentParticipantsNum());
		aroundInfoDTO.setTotalAmout(around.getTotalAmout());
		int status = around.getStatus();
		boolean inAround = participantService.isInAround(aroundId, user.getUserId());
		if (status == 2 || status == 3 ) {
			aroundInfoDTO.setIsOver(true);
			aroundInfoDTO.setDesc("已结束");
		} else {
			aroundInfoDTO.setIsOver(false);
			aroundInfoDTO.setDesc(inAround ? "已参与" : "立即参与");
		}

		List<JumpParticipant> participants;
		if (status == 2 || status == 3 ) {
			participants = participantService.getParticipantsOrderByRank(aroundId);
		} else {
			participants = participantService.getParticipants(aroundId);
		}
		List<AroundInfoDTO.Participant> ps = Lists.newArrayList();
		for (JumpParticipant participant : participants) {
			AroundInfoDTO.Participant p = new AroundInfoDTO.Participant();
			p.setEndTime(participant.getUpdateTime());
			p.setName(participant.getParticipantName());
			if (status == 2 || status == 3 ) {
				p.setPoint(String.valueOf(participant.getPoint()));
				p.setRank(String.valueOf(participant.getRankNum()));
			} else {
				p.setPoint("***");
				p.setRank("回合未结束");
			}
			p.setIsWin(participant.getIsWin());
			ps.add(p);
		}
		aroundInfoDTO.setParticipants(ps);

		return aroundInfoDTO;
	}
}
