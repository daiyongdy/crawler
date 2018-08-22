package com.crawler.controller;

import com.crawler.model.WebMessage;
import com.crawler.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by daiyong on 2018/8/22.
 * email daiyong@qiyi.com
 */
@RestController
@RequestMapping("/api/u")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping("/record")
	public WebMessage record() {
		return WebMessage.build(userService.records());
	}

}
