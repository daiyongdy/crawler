package com.crawler.web.ethrescan;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.crawler.util.httpclient.CoohuaHttpClient;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by daiyong on 2018/7/19.
 * email daiyong@qiyi.com
 */
public class ApiProvider {

	private static final Logger logger = LogManager.getLogger("sysLog");

	private static final String MAIN_URL = "https://api.blockcypher.com/v1/eth/main";

	private static final String NEXT_URL = "https://api.blockcypher.com/v1/eth/main/blocks/";

	public static String getFirstHeight(String id) {
		int times = 10;
		for (int i = 0; i < times; i++) {
			logger.info("[etherscan ApiProvider getFirstHeight] 第 " + (i+1) + " 次获取当前height, id:{}", id);
			try {
				String json = CoohuaHttpClient.get(MAIN_URL, "");
				JSONObject jsonObject = JSONObject.parseObject(json);
				String currentHeight = jsonObject.getString("height");
				logger.info("[etherscan ApiProvider getFirstHeight] 第 " + (i+1) + " 次获取当前height, id:{}, currentHeight:{}", id, currentHeight);
				return currentHeight;
			} catch (Exception e) {
				logger.error("[etherscan ApiProvider getFirstHeight] 异常, id:{}, e:{}", id, ExceptionUtils.getStackTrace(e));
			}
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return StringUtils.EMPTY;
	}

	public static Line getNextLine(String currentHeight, String id, boolean retry) {
		String nextHeight = String.valueOf( (Integer.valueOf(currentHeight) + 1) );
		int times = retry ? 1 : 25;
		for (int i = 0; i < times; i++) {
			logger.info("[etherscan ApiProvider getNextLine] 第 " + (i+1) + " 次获取下一height, id:{}", id);
			try {
				String json = CoohuaHttpClient.get(NEXT_URL + nextHeight, "");
				logger.info("[etherscan ApiProvider getNextLine] 第 " + (i+1) + " 次获取下一height, id:{}, json:{}", id, json);
				JSONObject jsonObject = JSONObject.parseObject(json);
				String error = jsonObject.getString("error");
				if (StringUtils.isNotBlank(error)) {
					logger.info("[etherscan ApiProvider getNextLine] 第 " + (i+1) + " 次获取下一height,没有生成下一个height id:{}", id);
				} else {
					String hash = jsonObject.getString("hash").trim();
					String utcTime = jsonObject.getString("time");
					String chnTime = UTCToCST(utcTime);
					Line line = new Line();
					line.setHeight(nextHeight);
					line.setHash(hash);
					line.setTimestamp(chnTime);
					logger.info("[etherscan ApiProvider getNextLine] 第 " + (i+1) + " 次获取下一height, id:{}, line:{}", id, JSON.toJSONString(line));
					return line;
				}
			} catch (Exception e) {
				logger.error("[etherscan ApiProvider getNextLine] 异常, id:{}, e:{}", id, ExceptionUtils.getStackTrace(e));
			}
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static String UTCToCST(String UTCStr) throws ParseException {
		Date date = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		date = sdf.parse(UTCStr);

		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf2.format(date);
	}

	public static void main(String[] args) {
//		String firstHeight = getFirstHeight("123");
//		System.out.println("+++++++++++++" + firstHeight);

//		try {
//			System.out.println("+++++++++++" + UTCToCST("2015-07-30T15:28:30Z"));
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}

		Line sdf = getNextLine("5991846", "sdf", false);
		System.out.println("+++++++++" + JSON.toJSONString(sdf));

	}

}
