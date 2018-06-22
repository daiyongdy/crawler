package com.crawler.queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 文章处理队列
 * Created by daiyong on 2017/5/16.
 * email daiyong@coohua.com
 */
public class Queue<T> {

	public static final int QUEUE_CAPACITY = 100000;

	public BlockingQueue QUEUE = new ArrayBlockingQueue(QUEUE_CAPACITY);

	public <T> void put(T dto) {
		try {
			QUEUE.put(dto);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public  <T> T get() {
		try {
			return (T) QUEUE.take();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return null;
	}

	public  int size() {
		return QUEUE.size();
	}

}
