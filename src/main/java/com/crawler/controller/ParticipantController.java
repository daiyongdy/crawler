package com.crawler.controller;

import com.crawler.model.WebMessage;
import com.crawler.service.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by daiyong on 2018/8/12.
 * email daiyong@qiyi.com
 */
@RestController
@RequestMapping("/api/participant")
public class ParticipantController {

	@Autowired
	private ParticipantService participantService;

	/**
	 * 客户积分
	 * @param score
	 * @return
	 */
	@RequestMapping("/score")
	public WebMessage score(@RequestParam int score) {
		boolean scoreResult = participantService.score(score);
		return WebMessage.build(scoreResult);
	}

}
