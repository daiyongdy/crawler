package com.crawler.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.crawler.dao.mapper.biz.JumpAroundBizMapper;
import com.crawler.dao.mapper.biz.JumpGameRecordBizMapper;
import com.crawler.dao.mapper.db.*;
import com.crawler.dao.model.db.*;
import com.crawler.exception.BizException;
import com.crawler.model.AroundInfoDTO;
import com.crawler.model.CheckDTO;
import com.crawler.model.WebUserDTO;
import com.crawler.model.WebUserHolder;
import com.crawler.util.DeviceUtil;
import com.crawler.util.HttpClientUtil;
import com.crawler.util.SignUtils;
import com.crawler.util.UUIDGenerator;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
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
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by daiyong on 2018/8/11.
 * email daiyong@qiyi.com
 */
@Service
@Transactional
public class AroundService {

	private static final Logger LOG = LogManager.getLogger("sysLog");
	private static final Logger generateSettleLOG = LogManager.getLogger("generateSettle");

	@Autowired
	private JumpAroundMapper aroundMapper;

	@Autowired
	private JumpAroundBizMapper aroundBizMapper;

	@Autowired
	private JumpParticipantMapper participantMapper;

	@Autowired
	private ParticipantService participantService;

	@Autowired
	private JumpSettleMapper settleMapper;

	@Autowired
	private JumpSettleDetailMapper settleDetailMapper;

	@Value("${api.user.balance.deduct}")
	private String API_USER_BALANCE_DEDUCT;

	@Value("${itobox.secret}")
	private String ITOBOX_SECRET;

	@Autowired
	private JumpIdGeneMapper idGeneMapper;

	@Autowired
	private JumpGameRecordMapper gameRecordMapper;

	@Autowired
	private JumpGameRecordBizMapper gameRecordBizMapper;


	/**
	 * 创建回合
	 * @param name
	 * @param minParticipant
	 * @param minMoney
	 */
	public JumpAround saveAround(String name, int minParticipant, String minMoney) {

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

		JumpAround unFinishedAround = aroundBizMapper.getUnFinishedAround(user.getUserId());
		if (unFinishedAround != null) {
			throw BizException.HAS_UNFINISHED_AROUND;
		}

		//创建回合
		JumpAround around = new JumpAround();
		around.setAroundId(UUIDGenerator.random32UUID());
		around.setAroundName(name);
		around.setMinParticipantsNum(minParticipant);
		around.setCurrentParticipantsNum(1);
		around.setMoney(minMoneyDecimal);
		around.setTotalAmout(minMoneyDecimal);
		around.setStatus(1); //状态： 0：新建   1：进行中 2： 已结束 3：已生成结算单
		around.setCreaterId(user.getUserId());
		around.setCreaterName(user.getUserName());
		around.setCreateTime(new Date());
		around.setUpdateTime(null);

		//回合序列
		gameRecordBizMapper.inc1();

		JumpIdGene jumpIdGene = new JumpIdGene();
		idGeneMapper.insert(jumpIdGene);
		around.setNo(jumpIdGene.getId());

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

		Map<String, Object> params = Maps.newHashMap();
		params.put("userId", user.getUserId());
		params.put("aid", around.getAroundId());
		params.put("amount", around.getMoney().toString());
		params.put("timestamp", String.valueOf(System.currentTimeMillis()));
		params.put("sign", SignUtils.sign(params, ITOBOX_SECRET));
		try {
			String result = HttpClientUtil.httpGetRequest(API_USER_BALANCE_DEDUCT, params, 10000, 10000);
			LOG.info("创建回合扣减用户余额, params:{}, result:{}", JSON.toJSONString(params), result);

			JSONObject resultObject = JSON.parseObject(result);
			if (resultObject.getIntValue("RET") == 1) {
				LOG.info("创建回合扣减用户余额成功, params:{}", JSON.toJSONString(params));
			} else {
				LOG.error("创建回合调用itobox扣减余额接口失败, params:{}", JSON.toJSONString(params));
				throw BizException.DEDUCT_BALANCE_FAIL;
			}
		} catch (URISyntaxException e) {
			LOG.error("创建回合调用itobox扣减余额接口异常, params:{}", JSON.toJSONString(params), e);
			throw BizException.DEDUCT_BALANCE_FAIL;
		}
		LOG.info("创建回合成功, userId:{}, userName:{}, around:{}", user.getUserId(), user.getUserName(), JSON.toJSONString(around));

		return around;

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
			JumpAround unFinishedAround = aroundBizMapper.getUnFinishedAround(user.getUserId());
			if (unFinishedAround != null) {
				checkDTO.setAroundId(aroundId);
				int status = unFinishedAround.getStatus();
				if (status == 2 || status == 3) {
					checkDTO.setIsAroundOver(true);
				} else {
					checkDTO.setIsAroundOver(false);
				}
				checkDTO.setAroundId(unFinishedAround.getAroundId());
				checkDTO.setIsSelfCreate(unFinishedAround.getCreaterId().equals(user.getUserId()));
				checkDTO.setHasBalance(user.getBalance().compareTo(BigDecimal.ZERO) > 0);
				checkDTO.setIsBalanceEnough(user.getBalance().compareTo(unFinishedAround.getMoney()) > 0);
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
//		boolean inAround = participantService.isInAround(aroundId, user.getUserId());
		JumpParticipant participant1 = participantService.getParticipant(aroundId, user.getUserId());
		if (status == 2 || status == 3 ) {
			aroundInfoDTO.setIsOver(true);
			aroundInfoDTO.setDesc("已结束");
		} else {
			aroundInfoDTO.setIsOver(false);
			boolean joinAround = participant1 != null;
			boolean finishGame = participant1 != null ? participant1.getIsOver() ? true : false : false;
//			aroundInfoDTO.setDesc(participant1 != null ? "已参与" : "立即参与");
			aroundInfoDTO.setDesc((joinAround && finishGame) ? "已参与" : "开始游戏");
		}

		//是否完成游戏
		aroundInfoDTO.setIsFinishGame(participant1 != null ? participant1.getIsOver() ? true : false : false);

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
			if (participant.getStartTime() != null && participant.getUpdateTime() != null) {
				p.setCostTime(
						Long.valueOf(participant.getUpdateTime().getTime() - participant.getStartTime().getTime()).intValue() / 1000
				);
			}
			ps.add(p);
		}
		aroundInfoDTO.setParticipants(ps);

		return aroundInfoDTO;
	}

	/**
	 * 生成结算单
	 * @param around
	 */
	public void generateSettle(JumpAround around) {

		JumpSettle settle = new JumpSettle();
		settle.setAroundId(around.getAroundId());
		settle.setHasSettleFinished(false);
		settle.setCreateTime(new Date());
		settleMapper.insertSelective(settle);

		List<JumpSettleDetail> settleDetails = Lists.newArrayList();

		List<JumpParticipant> participants = participantService.getParticipantsOrderByRank(around.getAroundId());
		int size = participants.size();

		List<JumpGameRecord> gameRecords = Lists.newArrayList();

		if (size <= 10) {
			JumpParticipant participant = participants.get(0);
			JumpSettleDetail settleDetail = new JumpSettleDetail();
			settleDetail.setSettleId(settle.getSettleId());
			settleDetail.setUserId(participant.getUserId());
			settleDetail.setParticipantName(participant.getParticipantName());
			settleDetail.setAroundId(around.getAroundId());
			BigDecimal prize = around.getTotalAmout().multiply(new BigDecimal(0.9));
			BigDecimal prizeFinal = prize.setScale(4, BigDecimal.ROUND_DOWN);
			settleDetail.setPrize(prizeFinal);
			settleDetail.setType(1);
			settleDetail.setHasSettleFinished(false);
			settleDetail.setCreateTime(new Date());
			settleDetails.add(settleDetail);

			//用户游戏记录
			for (JumpParticipant jumpParticipant : participants) {
				JumpGameRecord gameRecord = new JumpGameRecord();
				gameRecord.setUserId(String.valueOf(jumpParticipant.getUserId()));
				gameRecord.setGameNo(around.getNo());
				gameRecord.setMoney(around.getMoney());
				gameRecord.setRankNum(jumpParticipant.getRankNum());
				gameRecord.setIncome(jumpParticipant.getRankNum() == 1 ? prizeFinal : BigDecimal.ZERO);
				gameRecord.setCostTime((jumpParticipant.getUpdateTime().getTime() - jumpParticipant.getStartTime().getTime()) / 1000);
				gameRecord.setCreateTime(new Date());
				gameRecords.add(gameRecord);
			}

		} else {
			BigDecimal plus = around.getTotalAmout().subtract(around.getTotalAmout().multiply(new BigDecimal("0.1")));
			int winnerNum = 0;
			for (JumpParticipant participant : participants) {
				if (participant.getIsWin()) {
					winnerNum++;
				}
			}
			for (JumpParticipant participant : participants) {

				JumpGameRecord gameRecord = new JumpGameRecord();
				gameRecord.setUserId(String.valueOf(participant.getUserId()));
				gameRecord.setGameNo(around.getNo());
				gameRecord.setMoney(around.getMoney());
				gameRecord.setRankNum(participant.getRankNum());
				gameRecord.setCostTime((participant.getUpdateTime().getTime() - participant.getStartTime().getTime()) / 1000);
				gameRecord.setCreateTime(new Date());

				if (participant.getIsWin()) {
					JumpSettleDetail settleDetail = new JumpSettleDetail();
					settleDetail.setSettleId(settle.getSettleId());
					settleDetail.setUserId(participant.getUserId());
					settleDetail.setParticipantName(participant.getParticipantName());
					settleDetail.setAroundId(around.getAroundId());
					//冠军
					if (participant.getRankNum() == 1) {
						BigDecimal prize = plus.multiply(new BigDecimal("0.5"));
						BigDecimal prizeFinal = prize.setScale(4,BigDecimal.ROUND_DOWN);
						settleDetail.setPrize(prizeFinal);
						gameRecord.setIncome(prizeFinal);
					} else {
						BigDecimal prize = plus.multiply(new BigDecimal("0.5"));
						BigDecimal prizeFinal = prize.divide(new BigDecimal(winnerNum - 1), 4, BigDecimal.ROUND_DOWN);
						settleDetail.setPrize(prizeFinal);
						gameRecord.setIncome(prizeFinal);
					}
					settleDetail.setType(1);
					settleDetail.setHasSettleFinished(false);
					settleDetail.setCreateTime(new Date());
					settleDetails.add(settleDetail);
				} else {
					gameRecord.setIncome(BigDecimal.ZERO);
				}

				gameRecords.add(gameRecord);
			}
		}

		JumpSettleDetail settleDetail = new JumpSettleDetail();
		settleDetail.setSettleId(settle.getSettleId());
		settleDetail.setUserId(around.getCreaterId());
		settleDetail.setParticipantName(around.getCreaterName());
		settleDetail.setAroundId(around.getAroundId());
		BigDecimal prize = around.getTotalAmout().multiply(new BigDecimal("0.08"));
		BigDecimal prizeFinal = prize.setScale(4,BigDecimal.ROUND_DOWN);
		settleDetail.setPrize(prizeFinal);
		settleDetail.setType(2);
		settleDetail.setHasSettleFinished(false);
		settleDetail.setCreateTime(new Date());
		settleDetails.add(settleDetail);

		//添加用户游戏记录
		for (JumpGameRecord gameRecord : gameRecords) {
			if (gameRecord.getUserId().equals(settleDetail.getUserId())) {
				gameRecord.setIncome(gameRecord.getIncome().add(settleDetail.getPrize()));
			}
			gameRecordMapper.insertSelective(gameRecord);
		}

		around.setStatus(3);
		around.setUpdateTime(new Date());
		aroundMapper.updateByPrimaryKeySelective(around);

		for (JumpSettleDetail jumpSettleDetail : settleDetails) {
			settleDetailMapper.insertSelective(jumpSettleDetail);
		}

		generateSettleLOG.info("回合生成结算单结束 aroundId:{}, aroundName:{}, winnerSize:{}",
				around.getAroundId(), around.getAroundName(), settleDetails.size());

	}

	public static void main(String[] args) {
		BigDecimal decimal = new BigDecimal("1.12345");
		System.out.println(decimal);
		BigDecimal setScale = decimal.setScale(3,BigDecimal.ROUND_HALF_DOWN);
		System.out.println(setScale);
	}

	public List<JumpAround> getFinishedAround() {
		return aroundBizMapper.getFinishedAround();
	}
}
