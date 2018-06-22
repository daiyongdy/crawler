package com.crawler.pool;

import com.crawler.util.httpclient.CoohuaHttpClient;
import com.crawler.util.httpclient.HttpClientUtil;
import com.crawler.util.httpclient.common.HttpConfig;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by daiyong on 2018/5/22.
 * email daiyong@qiyi.com
 */
public class IPCrawler {

	private static Logger proxyLog = LogManager.getLogger("proxyLog");

	private static final Logger logger = LogManager.getLogger(IPCrawler.class);

	private static final String XICI = "http://www.xicidaili.com/nn/" ;

	private static final String SDK_URL = "http://api.tkdaili.com/api/getiplist.aspx?vkey=3736EC42C98687DA282288ED70A33F71&num=100&style=3&country=CN";

	public static void crawlerIps() {

		//项目启动 抓取5页
		/*for (int i = 1; i <= 5; i++) {
			getIpsByPage(i);
		}

		logger.info("ip 爬虫 爬取 西刺前5页数据完毕");*/

		HttpConfig httpConfig = HttpConfig.custom().url(SDK_URL);
		try {
			String value = HttpClientUtil.get(httpConfig);

			String[] httpHostArr = value.split("\r\n");

			if (httpHostArr.length <= 0) {
				proxyLog.error("请求tk异常, 没有数据");
				throw new RuntimeException("请求tk异常, 没有数据");
			}

			for (String httpHostStr : httpHostArr) {
				String[] httpHostPair = httpHostStr.split(":");
				HttpHost httpHost = new HttpHost(httpHostPair[0], Integer.valueOf(httpHostPair[1]));
				IPPool.offer(httpHost);
			}

		} catch (Exception e) {
			proxyLog.error("请求tk异常, e:{}", ExceptionUtils.getStackTrace(e));
			throw new RuntimeException("请求tk异常", e);
		}

	}

	public static void getIpsByPage(int i) {
		String url = XICI + i;
		String ipHtml = CoohuaHttpClient.get(url, "");
		Document ipDocument = Jsoup.parse(ipHtml);

		Elements trs = ipDocument.select("table#ip_list tr");
		for (int j = 1; j < trs.size(); j++) {
			Element tr = trs.get(j);
			Elements tds = tr.select("td");
			String ip = tds.get(1).html();
			String host = tds.get(2).html();
			HttpHost httpHost = new HttpHost(ip, Integer.valueOf(host));
			IPPool.offer(httpHost);
		}
	}

	public static void main(String[] args) {
//		new IPCrawler().crawlerIps();

	}

}
