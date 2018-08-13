package com.crawler.service;

import com.crawler.dao.mapper.biz.JumpAroundBizMapper;
import com.crawler.dao.mapper.db.JumpAroundMapper;
import com.crawler.dao.mapper.db.JumpParticipantMapper;
import com.crawler.dao.model.db.JumpAround;
import com.crawler.dao.model.db.JumpParticipant;
import com.crawler.exception.BizException;
import com.crawler.model.CheckDTO;
import com.crawler.model.WebUserDTO;
import com.crawler.model.WebUserHolder;
import com.crawler.util.DeviceUtil;
import com.crawler.util.UUIDGenerator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by daiyong on 2018/8/11.
 * email daiyong@qiyi.com
 */
@Service
@Transactional
public class AroundService {

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
	 * @param pNum
	 * @param minPrice
	 */
	public void saveAround(String name, int pNum, String minPrice) {

		WebUserDTO user = WebUserHolder.getUser();

		BigDecimal minPriceDecimal = new BigDecimal(minPrice);
		BigDecimal balance = user.getBalance();
		if (balance.compareTo(minPriceDecimal) < 0) {
			throw BizException.BALANCE_NOT_EMPTY;
		} else {

			//创建回合
			JumpAround around = new JumpAround();
			around.setAroundId(UUIDGenerator.random32UUID());
			around.setAroundName(name);
			around.setMinParticipantsNum(pNum);
			around.setCurrentParticipantsNum(1);
			around.setMoney(minPriceDecimal);
			around.setTotalAmout(minPriceDecimal);
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


		}


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
			JumpAround around = aroundMapper.selectByPrimaryKey(aroundId);
			if (null == around) {
				throw BizException.AROUND_NOT_EXISTS;
			}
			checkDTO.setAroundId(aroundId);
			int status = around.getStatus();
			if (status == 3 || status == 4) {
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
				JumpAround around = aroundMapper.selectByPrimaryKey(unOverAroundId);
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
}
