package com.crawler.service;

import com.crawler.dao.mapper.db.EtherScanMapper;
import com.crawler.dao.model.db.EtherScan;
import com.crawler.web.ethrescan.ApiProvider;
import com.crawler.web.ethrescan.HtmlProvider;
import com.crawler.web.ethrescan.Line;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by daiyong on 2018/7/18.
 * email daiyong@qiyi.com
 */
@Service
public class EtherScanService {

	private static final Logger logger = LogManager.getLogger(EtherScanService.class);

	@Autowired
	private EtherScanMapper etherScanMapper;

	public void addEtherScan(String id) {

		EtherScan etherScan1 = etherScanMapper.selectByPrimaryKey(id);
		if (etherScan1 != null) {
			logger.error("[etherscan] EtherScanService 当前id已经存在 直接返回, id:{}", id);
			return;
		}

		EtherScan etherScan = new EtherScan();
		etherScan.setId(id);
		String currHeight = getFirstHeight(id);
		if (!StringUtils.isNotBlank(currHeight)) {
			logger.error("[etherscan] EtherScanService 没有获取当前height, id:{}", id);
		}
		etherScan.setCurrHeight(currHeight);
		etherScan.setCreatedAt(new Date());
		etherScanMapper.insert(etherScan);

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		Line line = getNextLine(currHeight, id);
		if (line != null) {
			etherScan.setNextHeight(line.getHeight());
			etherScan.setHash(line.getHash());
			etherScan.setTimestamp(line.getTimestamp());
			etherScan.setIsProcessed(true);
			etherScan.setUpdatedAt(new Date());
			etherScanMapper.updateByPrimaryKey(etherScan);
		} else {
			logger.error("[etherscan] EtherScanService 没有获取下一个height, id:{}", id);
		}

	}

	/**
	 *  获取第一个height
	 */
	private String getFirstHeight(String id) {
		String currentHeight = HtmlProvider.getFirstHeight(id);
		if (!StringUtils.isNotBlank(currentHeight)) {
			currentHeight = ApiProvider.getFirstHeight(id);
		}
		return currentHeight;
	}

	/**
	 * 获取下一个Line
	 * @param currHeight
	 * @param id
	 * @return
	 */
	private Line getNextLine(String currHeight, String id) {
		Line line = HtmlProvider.getNextLine(currHeight, id);
		if (line == null) {
			line = ApiProvider.getNextLine(currHeight, id);
		}
		return line;
	}


}
