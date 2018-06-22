package com.crawler.pool;


import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by daiyong on 2018/5/22.
 * email daiyong@qiyi.com
 */
public class IPPool {

	private static final Queue<HttpHost> QUEUE = new LinkedList<HttpHost>();

	public static void offer(HttpHost httpHost) {
		QUEUE.offer(httpHost);
	}

	public static HttpHost poll() {
		return QUEUE.poll();
	}

	public static int size() {
		return QUEUE.size();
	}

}
