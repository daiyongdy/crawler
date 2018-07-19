package com.crawler.web.ethrescan;

import com.alibaba.fastjson.JSON;
import com.crawler.util.httpclient.CoohuaHttpClient;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by daiyong on 2018/7/19.
 * email daiyong@qiyi.com
 */
public class HtmlProvider {

	private static final Logger logger = LogManager.getLogger(HtmlProvider.class);

	private static final String MAIN_URL = "https://etherscan.io/blocks";

	private static final String NEXT_URL = "https://etherscan.io/block/";

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

	public static String getFirstHeight(String id) {
		int times = 2;
		for (int i = 0; i < times; i++) {
			try {
				logger.info("[echerscan HtmlProvider getFirstHeight]" + " 第" + (i + 1) + "次爬取当前height, id:{}", id);
				String html = CoohuaHttpClient.get(MAIN_URL, "");
				Document document = Jsoup.parse(html);
				Element table = document.select("table.table.table-hover").get(0);
				Element tbody_tr = table.select("tbody tr").get(0);
				Elements tds = tbody_tr.select("td");
				Element td = tds.get(0);
				Element a = td.select("a").get(0);
				String currentHeight = a.text().trim();
				logger.info("[echerscan HtmlProvider getFirstHeight]" + " 第" + (i + 1) + "次爬取当前height, id:{}, currentHeight:{}", id, currentHeight);
				return currentHeight;
			} catch (Exception e) {
				logger.info("[echerscan HtmlProvider getFirstHeight] " + id + " 第" + (i + 1) + "次爬取当前height异常, id:{}, e:{}", id, ExceptionUtils.getStackTrace(e));
			}
		}
		return StringUtils.EMPTY;
	}

	public static Line getNextLine(String currentHeight, String id) {

		String nextHeight = String.valueOf( (Integer.valueOf(currentHeight) + 1) );

		int times = 5;
		for (int i = 0; i < times; i++) {
			try {
				logger.info("[etherscan HtmlProvider getNextLine] 第 " + (i+1) + " 次获取下一height, id:{}", id);

				String nextHtml = CoohuaHttpClient.get(NEXT_URL + nextHeight, null);

				if (nextHtml.contains("Unable to locate Block")) {
					logger.error("[echerscan HtmlProvider getNextLine] 没有产生下一个height id:{}, nextHeight:{}", id, nextHeight);
				} else {
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

					Line line = new Line();
					line.setHeight(nextHeight);
					line.setHash(hash);
					line.setTimestamp(timeStamp);

					logger.info("[echerscan  HtmlProvider getNextLine] 获取下一个height完成 times:{}, line：{}", (i+1), JSON.toJSONString(line));
					return line;
				}
			} catch (Exception e) {
				logger.error("[etherscan HtmlProvider getNextLine] 异常, id:{}, e:{}", id, ExceptionUtils.getStackTrace(e));
			}

			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}

		return null;
	}

}
