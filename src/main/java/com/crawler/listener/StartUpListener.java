package com.crawler.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Created by daiyong on 2018/6/22.
 * email daiyong@qiyi.com
 */
@WebListener
public class StartUpListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("123132");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {

	}
}
