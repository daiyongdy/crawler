package com.crawler.runnable;

import com.crawler.dao.model.db.BetterCoin;
import com.crawler.service.BetterCoinService;
import com.crawler.service.BetterCrawlerService;
import com.crawler.util.SpringContextUtil;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 *
 * @author daiyong
 * @date 2018/5/21
 * email daiyong@qiyi.com
 * 没有代理 所以采用单线程爬取 否则有可能被封ip
 */
public class BetterCrawlerRunnable implements Runnable{
	
	private static final Logger logger = LogManager.getLogger(BetterCrawlerRunnable.class);

	private static final BetterCoinService betterCoinService = SpringContextUtil.getBean("betterCoinService");
	private static final BetterCrawlerService betterCrawlerService = SpringContextUtil.getBean("betterCrawlerService");

	@Override
	public void run() {
		while (true) {
			try {

				List<BetterCoin> betterCoins = betterCoinService.getAllCoin();
//				List<BetterCoin> betterCoins = betterCoinService.getById(36L);

				//遍历所有的币
				for (BetterCoin betterCoin : betterCoins) {
					try {
						logger.info("[better crawler] 当前币种：{} 爬取开始", betterCoin.getName());

						betterCrawlerService.crawler(betterCoin);

						logger.info("[better crawler] 当前币种：{} 爬取完毕", betterCoin.getName());

						Thread.sleep(3000);
					} catch (Exception e) {
						logger.info("[better crawler] 当前币种：{} 爬取异常, {}", betterCoin.getName(), ExceptionUtils.getStackTrace(e));
					}
				}

			} catch (Exception e) {
				logger.error("本次爬取失败, e:{}", ExceptionUtils.getStackTrace(e));
			}
		}


	}

}
