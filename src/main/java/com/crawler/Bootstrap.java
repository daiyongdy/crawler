package com.crawler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
//@ServletComponentScan
@MapperScan({"com.crawler.dao.mapper.db","com.crawler.dao.mapper.biz"})
@EnableScheduling
public class Bootstrap {

	private static Logger logger = LogManager.getLogger("Bootstrap");

	public static void main(String[] args) {
		SpringApplication.run(Bootstrap.class, args);

//		BetterCoinService betterCoinService = SpringContextUtil.getBean("betterCoinService");
//
//		List<BetterCoin> betterCoins = betterCoinService.getAllCoin();
//		for (BetterCoin betterCoin : betterCoins) {
//			Constants.BETTER_COINS.add(betterCoin);
//		}

	/*	CrawlerApiCoinService crawlerApiCoinService = SpringContextUtil.getBean("crawlerApiCoinService");
		List<CrawlerApiCoin> allApiCoin = crawlerApiCoinService.getAllApiCoin();
		for (CrawlerApiCoin coin : allApiCoin) {
			Constants.API_COINS.add(coin);
		}

		new Thread(new BishijieRunnable()).start();
		logger.info("币世界爬虫启动....");*/
	}

}
