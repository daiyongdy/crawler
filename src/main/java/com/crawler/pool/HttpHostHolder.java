package com.crawler.pool;

/**
 * Created by daiyong on 2017/5/11.
 * email daiyong@coohua.com
 */
public class HttpHostHolder {

	public static volatile HttpHost httpHost;

	static {
		setProxy();
	}

	public static void switchProxy() {
		setProxy();
	}

	private static void setProxy() {
		httpHost = IPPool.poll();

		if (IPPool.size() < 50) {
			IPCrawler.crawlerIps();
		}

	}
}
