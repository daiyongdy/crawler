package com.crawler.model;

import java.math.BigDecimal;

public class WebUserHolder {

	private static ThreadLocal<WebUserDTO> threadLocal = new ThreadLocal<WebUserDTO>();

	public static void setUser(WebUserDTO userDto) {
		threadLocal.set(userDto);
	}

	public static WebUserDTO getUser() {

		WebUserDTO userDTO = new WebUserDTO();
		userDTO.setUserId("000000000");
		userDTO.setUserName("代勇");
		userDTO.setBalance(new BigDecimal(100));

		return userDTO;

//		return threadLocal.get();
	}

}
