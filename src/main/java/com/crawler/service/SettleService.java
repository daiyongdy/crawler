package com.crawler.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.crawler.dao.mapper.db.JumpSettleDetailMapper;
import com.crawler.dao.mapper.db.JumpSettleMapper;
import com.crawler.dao.model.db.JumpSettle;
import com.crawler.dao.model.db.JumpSettleDetail;
import com.crawler.dao.model.db.JumpSettleDetailExample;
import com.crawler.dao.model.db.JumpSettleExample;
import com.crawler.util.HttpClientUtil;
import com.crawler.util.SignUtils;
import com.google.common.collect.Maps;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by daiyong on 2018/8/15.
 * email daiyong@qiyi.com
 */
@Service
public class SettleService {

	private static final Logger LOG = LogManager.getLogger("settle");

	@Autowired
	private JumpSettleMapper settleMapper;

	@Autowired
	private JumpSettleDetailMapper settleDetailMapper;

	@Value("${api.user.balance.add}")
	private String API_USER_BALANCE_ADD;

	@Value("${itobox.secret}")
	private String ITOBOX_SECRET;

	/**
	 * 获取未结束结算单
	 */
	public List<JumpSettle> getUnfinishedSettle() {
		JumpSettleExample example = new JumpSettleExample();
		JumpSettleExample.Criteria criteria = example.createCriteria();
		criteria.andHasSettleFinishedEqualTo(false);
		List<JumpSettle> jumpSettles = settleMapper.selectByExample(example);
		return jumpSettles;
	}

	/**
	 * 派奖
	 * @param settle
	 * 不加事务
	 */
	public void settle(JumpSettle settle) {
		JumpSettleDetailExample example = new JumpSettleDetailExample();
		JumpSettleDetailExample.Criteria criteria = example.createCriteria();
		criteria.andSettleIdEqualTo(settle.getSettleId());
		List<JumpSettleDetail> settleDetails = settleDetailMapper.selectByExample(example);

		int sucNum = 0;
		for (JumpSettleDetail settleDetail : settleDetails) {
			try {

				if (settleDetail.getHasSettleFinished()) {
					LOG.info("派奖开始 已经派奖 settleId:{}, settleDetailId:{}, userId:{}, aroundId:{}, prize:{}",
							settle.getSettleId(), settleDetail.getJumpSettleDetailId(), settleDetail.getUserId(), settle.getAroundId(), settleDetail.getPrize());
					sucNum++;
					continue;
				}

				LOG.info("派奖开始 settleId:{}, settleDetailId:{}, userId:{}, aroundId:{}, prize:{}",
								settle.getSettleId(), settleDetail.getJumpSettleDetailId(), settleDetail.getUserId(), settle.getAroundId(), settleDetail.getPrize());
				Map<String, Object> params = Maps.newHashMap();
				params.put("userId", settleDetail.getUserId());
				params.put("aid", settle.getAroundId());
				params.put("amount", settleDetail.getPrize().toString());
				params.put("timestamp", String.valueOf(System.currentTimeMillis()));
				params.put("type", settleDetail.getType());
				params.put("sign", SignUtils.sign(params, ITOBOX_SECRET));
				String result = HttpClientUtil.httpGetRequest(API_USER_BALANCE_ADD, params, 10000, 10000);
				LOG.info("itobox 用户派奖, params:{}, result:{}", JSON.toJSONString(params), result);

				JSONObject resultObject = JSONObject.parseObject(result);
				if (resultObject.getIntValue("RET") == 1) {
					LOG.info("派奖成功 settleId:{}, settleDetailId:{}, userId:{}, aroundId:{}, prize:{}",
							settle.getSettleId(), settleDetail.getJumpSettleDetailId(), settleDetail.getUserId(), settle.getAroundId(), settleDetail.getPrize());
					settleDetail.setHasSettleFinished(true);
					settleDetail.setUpdateTime(new Date());
					settleDetailMapper.updateByPrimaryKeySelective(settleDetail);
					sucNum++;
				} else {
					LOG.info("派奖失败 settleId:{}, settleDetailId:{}, userId:{}, aroundId:{}, prize:{}",
							settle.getSettleId(), settleDetail.getJumpSettleDetailId(), settleDetail.getUserId(), settle.getAroundId(), settleDetail.getPrize());
				}

			} catch (Exception e) {
				LOG.info("派奖异常 settleId:{}, settleDetailId:{}, userId:{}, aroundId:{}, prize:{}",
						settle.getSettleId(), settleDetail.getJumpSettleDetailId(), settleDetail.getUserId(), settle.getAroundId(), settleDetail.getPrize(), e);
			}
		}

		if (sucNum == settleDetails.size()) {
			settle.setHasSettleFinished(true);
			settle.setUpdateTime(new Date());
			settleMapper.updateByPrimaryKeySelective(settle);
		}

	}

}
