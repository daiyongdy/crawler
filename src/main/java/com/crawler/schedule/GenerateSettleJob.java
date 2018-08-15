package com.crawler.schedule;

import com.crawler.dao.model.db.JumpAround;
import com.crawler.service.AroundService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by daiyong on 2018/8/14.
 * email daiyong@qiyi.com
 * 生成结算单job
 */
@Component
public class GenerateSettleJob {

	@Autowired
	private AroundService aroundService;

	private static final Logger LOG = LogManager.getLogger("generateSettle");

	@Scheduled(cron = "0/10 * * * * ?")
	public void generateSettle() {
		LOG.info("查询已结束回合 生成结算单开始");


		List<JumpAround> finishedAroundList = aroundService.getFinishedAround();
		LOG.info("当前共有 {} 已结束回合", finishedAroundList.size());

		for (JumpAround around : finishedAroundList) {
			try {
				LOG.info("为当前回合生成结算单开始, aroundId:{}, aroundName:{}", around.getAroundId(), around.getAroundName());
				aroundService.generateSettle(around);
				LOG.info("为当前回合生成结算单完成, aroundId:{}, aroundName:{}", around.getAroundId(), around.getAroundName());
			} catch (Exception e) {
				LOG.error("当前回合生成结算单异常 aroundId:{}, aroundName:{}", around.getAroundId(), around.getAroundName(), e);
			}
		}

		LOG.info("查询已结束回合 生成结算单结束");
	}

}
