package com.crawler;

import com.crawler.dao.model.db.BishijieKeyword;
import com.crawler.runnable.BishijieRunnable;
import com.crawler.service.BishijieService;
import com.crawler.util.SpringContextUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
//@ServletComponentScan
@MapperScan({"com.crawler.dao.mapper.db","com.crawler.dao.mapper.biz"})
/**
 *  cui zhen
 */
public class Bootstrap {

	private static Logger logger = LogManager.getLogger("Bootstrap");

	public static void main(String[] args) {
		SpringApplication.run(Bootstrap.class, args);

		BishijieService bishijieService = SpringContextUtil.getBean("bishijieService");

		List<BishijieKeyword> all = bishijieService.getAll();
		for (BishijieKeyword keyword : all) {
			Constants.KEYWORDS.add(keyword.getKeyword());
		}

		new Thread(new BishijieRunnable()).start();
		logger.info("币世界爬虫启动....");
	}

}
