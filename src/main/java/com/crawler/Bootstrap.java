package com.crawler;

import com.crawler.runnable.BetterCrawlerRunnable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan({"com.crawler.dao.mapper.db","com.crawler.dao.mapper.biz"})
/**
 *  cui zhen
 */
public class Bootstrap {

	private static Logger logger = LogManager.getLogger("Bootstrap");

	public static void main(String[] args) {
		SpringApplication.run(Bootstrap.class, args);
		new Thread(new BetterCrawlerRunnable()).start();
		logger.info("爬虫启动....");
	}

}
