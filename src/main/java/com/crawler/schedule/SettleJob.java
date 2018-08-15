package com.crawler.schedule;

import com.crawler.dao.model.db.JumpSettle;
import com.crawler.service.SettleService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by daiyong on 2018/8/14.
 * email daiyong@qiyi.com
 * 结算job
 */
@Component
public class SettleJob {

	@Autowired
	private SettleService settleService;

	private static final Logger LOG = LogManager.getLogger("settle");

	@Scheduled(cron = "0/10 * * * * ?")
	public void generateSettle() {
		LOG.info("查询未结束结算单开始");

		List<JumpSettle> unfinishedSettle = settleService.getUnfinishedSettle();

		for (JumpSettle settle : unfinishedSettle) {
			try {
				settleService.settle(settle);
			} catch (Exception e) {
				LOG.error("处理发奖失败, settleId:{}", settle.getSettleId(), e);
			}
		}

		LOG.info("查询未结束结算单结束");
	}

}
