package com.crawler.util;

import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by daiyong on 2018/8/14.
 * email daiyong@qiyi.com
 */
public class SignUtils {

	public static String sign(Map<String, Object> params, String secret) {
		SortedMap<String, Object> sortedParams = new TreeMap<String, Object>(params);
		sortedParams.remove("sign");
		StringBuilder sb = new StringBuilder();
		for (String key : sortedParams.keySet()) {
			String val = String.valueOf(sortedParams.get(key));
			sb.append(key).append("=").append(StringUtils.defaultIfEmpty(val, "")).append("|");
		}
		return MD5Utils.getDigest(sb.append(secret).toString());
	}

}
