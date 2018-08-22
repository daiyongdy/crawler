package com.crawler.controller;

import com.crawler.dao.model.db.JumpAround;
import com.crawler.model.AroundInfoDTO;
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
@RequestMapping("/api/a")
public class AroundController {

	@Autowired
	private AroundService aroundService;

	@RequestMapping("/create")
	public WebMessage save(@RequestParam String name,
						   @RequestParam int mp,
						   @RequestParam String mm) {

		JumpAround around = aroundService.saveAround(name, mp, mm);

		return WebMessage.build(around);
	}

	@RequestMapping("/check")
	public WebMessage check(HttpServletRequest request, String aid) {
		CheckDTO checkDTO = aroundService.check(request, aid);
		return WebMessage.build(checkDTO);
	}

	@RequestMapping("/info")
	public WebMessage info(String aid)  {
		AroundInfoDTO aroundInfoDTO = aroundService.getAroundInfo(aid);
		return WebMessage.build(aroundInfoDTO);
	}


}
