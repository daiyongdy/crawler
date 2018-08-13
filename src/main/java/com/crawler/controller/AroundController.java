package com.crawler.controller;

import com.crawler.model.CheckDTO;
import com.crawler.model.WebMessage;
import com.crawler.service.AroundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by daiyong on 2018/8/11.
 * email daiyong@qiyi.com
 */
@RestController
@RequestMapping("/api/around")
public class AroundController {

	@Autowired
	private AroundService aroundService;

	@RequestMapping("/save")
	public WebMessage save(@RequestParam String name,
						   @RequestParam int pNum,
						   @RequestParam String minPrice) {

		aroundService.saveAround(name, pNum, minPrice);

		return WebMessage.DEFAULT;
	}

	@RequestMapping("/check")
	public WebMessage check(HttpServletRequest request, String aroundId) {
		CheckDTO checkDTO = aroundService.check(request, aroundId);
		return WebMessage.build(checkDTO);
	}


}
