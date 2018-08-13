package com.crawler;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
//@ServletComponentScan
@MapperScan({"com.crawler.dao.mapper.db","com.crawler.dao.mapper.biz"})
@EnableScheduling
public class Bootstrap {

	public static void main(String[] args) {
		SpringApplication.run(Bootstrap.class, args);
	}

}
