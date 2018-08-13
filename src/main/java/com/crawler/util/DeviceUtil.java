package com.crawler.util;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by daiyong on 2018/8/11.
 * email daiyong@qiyi.com
 */
public class DeviceUtil {

	public static boolean check(HttpServletRequest request){
		boolean isFromMobile=false;

		//获取ua，用来判断是否为移动端访问
		String userAgent = request.getHeader( "USER-AGENT" ).toLowerCase();
		if(null == userAgent){
			userAgent = "";
		}

		isFromMobile=CheckMobile.check(userAgent);

		return isFromMobile;
	}


}
