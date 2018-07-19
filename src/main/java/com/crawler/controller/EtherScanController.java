package com.crawler.controller;

import com.crawler.service.EtherScanService;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.Map;

/**
 * Created by daiyong on 2018/7/18.
 * email daiyong@qiyi.com
 */
@Controller
@RequestMapping("/eth")
public class EtherScanController {

	@Autowired
	private EtherScanService etherScanService;

	@RequestMapping("/start/{id}")
	@ResponseBody
	public Object store(@PathVariable String id) {
		final String finalId = id;
		new Thread(new Runnable() {
			@Override
			public void run() {
				etherScanService.addEtherScan(finalId);
			}
		}).start();
		Map<String, Boolean> result = Maps.newHashMap();
		result.put("result", true);
		return result;
	}



}
