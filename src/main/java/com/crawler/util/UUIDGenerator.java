package com.crawler.util;

import java.util.UUID;

/**
 * Created by daiyong on 2018/5/22.
 * email daiyong@qiyi.com
 */
public class UUIDGenerator {

	public static String random32UUID() {
		UUID uuid = UUID.randomUUID();
		String str = uuid.toString();
		// 去掉"-"符号
		String temp = str.substring(0, 8) + str.substring(9, 13)
				+ str.substring(14, 18) + str.substring(19, 23)
				+ str.substring(24);
		return temp.toUpperCase();
	}
}
