package com.crawler.service;

import com.alibaba.fastjson.JSON;
import com.crawler.dao.mapper.db.EtherScanMapper;
import com.crawler.dao.model.db.EtherScan;
import com.crawler.util.httpclient.CoohuaHttpClient;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by daiyong on 2018/7/18.
 * email daiyong@qiyi.com
 */
@Service
public class EtherScanService {

	private static final Logger logger = LogManager.getLogger(EtherScanService.class);
	private static final String URL = "https://etherscan.io/blocks";
	private static final String DETAIL_URL = "https://etherscan.io/block/";

	private static Map<String, String> monthMap = Maps.newHashMap();
	static {
		monthMap.put("Jan", "01");
		monthMap.put("Feb", "02");
		monthMap.put("Mar", "03");
		monthMap.put("Apr", "04");
		monthMap.put("May", "05");
		monthMap.put("Jun", "06");
		monthMap.put("Jul", "07");
		monthMap.put("Aug", "08");
		monthMap.put("Sep", "09");
		monthMap.put("Oct", "10");
		monthMap.put("Nov", "11");
		monthMap.put("Dec", "12");
	}

	@Autowired
	private EtherScanMapper etherScanMapper;

	public void addEtherScan(String id) {
		EtherScan etherScan = new EtherScan();
		etherScan.setId(id);
		String currHeight = getFirstHeight(id);
		etherScan.setCurrHeight(currHeight);
		etherScan.setCreateTime(new Date());
		etherScanMapper.insert(etherScan);

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		//爬取下一个
		Long nextHeight = Long.valueOf(currHeight) + 1;
		String nextUrl = DETAIL_URL + nextHeight;


		int times = 1;
		while (true) {
			String nextHtml = CoohuaHttpClient.get(nextUrl, null);
			if (times > 5) {
				logger.error("[echerscan] 获取下一个height 已经超过5次 跳出循环");
				break;
			}
			if (nextHtml.contains("Unable to locate Block")) {
				times++;
				try {
					logger.error("[echerscan] 没有产生下一个height nextHeight:{}", nextHeight);
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else {
				try {
					Document nextDetailDocument = Jsoup.parse(nextHtml);
					Element totalDiv = nextDetailDocument.getElementById("ContentPlaceHolder1_maintable");
					Element hashDiv = totalDiv.select("div").get(8);
					String hash = hashDiv.text().trim();

					String timeStamp = "";
					Element timestampDiv = totalDiv.select("div").get(4);
					Pattern pattern = Pattern.compile(".*\\((.*)\\)");
					Matcher matcher = pattern.matcher(timestampDiv.text().trim());
					if (matcher.find()) {
						String utcStr = matcher.group(1);
						utcStr = utcStr.replace("+UTC", "").replace("AM", "").replace("PM", "");
						String[] ymdArr = utcStr.trim().split(" ");
						String[] ymd = ymdArr[0].split("-");
						String year = ymd[2];
						String month = ymd[0];
						String date = ymd[1];
						timeStamp = year + "-" + monthMap.get(month) + "-" + date + " " + ymdArr[1];
					}
					etherScan.setNextHeight(String.valueOf(nextHeight));
					etherScan.setHash(hash);
					etherScan.setTimestamp(timeStamp);
					etherScan.setIsProcessed(true);
					etherScan.setUpdateTime(new Date());
					etherScanMapper.updateByPrimaryKey(etherScan);
					logger.info("[echerscan] 获取下一个height完成 times:{}, etherScan：{}", times, JSON.toJSONString(etherScan));
					times++;
					break;
				} catch (Exception e) {
					times++;
					logger.error("[echerscan] 获取下一个height异常 times:{}, e:{}", times, ExceptionUtils.getStackTrace(e));
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}
			}
		}

	}

	/**
	 *  获取第一个height
	 */
	private String getFirstHeight(String id) {
		int times = 3;

		for (int i = 0; i < times; i++) {
			try {
				logger.info("[echerscan] " + id + " 第" + i + "次爬取当前height");
				String html = CoohuaHttpClient.get(URL, "");
				Document document = Jsoup.parse(html);
				Element table = document.select("table.table.table-hover").get(0);
				Element tbody_tr = table.select("tbody tr").get(0);
				Elements tds = tbody_tr.select("td");
				Element td = tds.get(0);
				Element a = td.select("a").get(0);
				return a.text();
			} catch (Exception e) {
				logger.info("[echerscan] " + id + " 第" + i + "次爬取当前height异常, {}", ExceptionUtils.getStackTrace(e));
			}
		}

		logger.info("[echerscan] " + id + " 3次爬取异常 返回-1");

		return "-1";
	}


}
