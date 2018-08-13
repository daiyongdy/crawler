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
@RequestMapping("/api/p")
public class ParticipantController {

	@Autowired
	private ParticipantService participantService;

	/**
	 * 客户积分
	 * @param pts
	 * @return
	 */
	@RequestMapping("/report")
	public WebMessage score(@RequestParam int pts) {
		boolean scoreResult = participantService.score(pts);
		return WebMessage.build(scoreResult);
	}

	/**
	 * 参与游戏
	 * @param aid
	 * @return
	 */
	@RequestMapping("/join")
	public WebMessage join(@RequestParam String aid) {
		boolean joinResult = participantService.join(aid);
		return WebMessage.build(joinResult);
	}

}
