package com.crawler.model;

public class WebUserHolder {

	private static ThreadLocal<WebUserDTO> threadLocal = new ThreadLocal<WebUserDTO>();

	public static void setUser(WebUserDTO userDto) {
		threadLocal.set(userDto);
	}

	public static WebUserDTO getUser() {
		return threadLocal.get();
	}

}
