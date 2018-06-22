package com.crawler.web.wechat;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.crawler.dao.model.db.Article;
import com.crawler.dao.model.db.Coin;
import com.crawler.pool.HttpHost;
import com.crawler.pool.HttpHostHolder;
import com.crawler.service.ArticleService;
import com.crawler.util.Md5Utils;
import com.crawler.util.UUIDGenerator;
import com.crawler.util.httpclient.CoohuaHttpClient;
import com.crawler.util.httpclient.HttpClientUtil;
import com.crawler.util.httpclient.common.HttpConfig;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.http.client.HttpClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by daiyong on 2018/5/22.
 * email daiyong@qiyi.com
 */
@Service
public class WechatService {

	private static final Logger logger = LogManager.getLogger(WechatService.class);

	private static final String LIST_URL = "http://weixin.sogou.com/weixin?type=1&s_from=input&query={keyword}&ie=utf8&_sug_=n&_sug_type_=";

	private static final String COOKIE = "CXID={CXID}; usid=MFDNYP3iAUT1qUvX; SUV={SUV}; SUID={SUID}; ad=2yllllllll2zf$tZlllllVriyFwlllllhuZnDlllll9lllllRm2Wj5@@@@@@@@@@; ABTEST=1|1526635549|v1; IPLOC=CN1100; SNUID={SNUID}; JSESSIONID=aaaCG2YeJwlzYbZHzSknw";


	@Autowired
	private ArticleService articleService;

	public void crawler(Coin coin) {
		String url = LIST_URL.replace("{keyword}", coin.getCnName())
							 .replace("{page}", "1");
		logger.info("[wechat] 当前列表页url：{}", url);

		List<Map<String, Object>> userLinks = getUserLinks(url);
		logger.info("[wechat] 当前列表页url: {}, 结果：{}", url, JSON.toJSONString(userLinks));

		for (Map<String, Object> userLink : userLinks) {
			String userName = (String) userLink.get("name");
			String link = (String) userLink.get("link");

			try {
				getPubArticle(userName, link, coin);
			} catch (Exception e) {
				logger.error("[wechat] 获取详情页异常, user:{}, link:{}, e:{}",
						userName, link, ExceptionUtils.getStackTrace(e));
			}

			/*try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}*/
		}

	}

	private void getPubArticle(String userName, String link, Coin coin) {

		String detailHtml;
		try {
//			detailHtml = wechatGet(link);
			detailHtml = getByProxy(link, "");
			logger.info("wechat debug url:{}, detailHtml:{}", link, detailHtml);
		} catch (Exception e) {
			logger.info("[wechat] 获取发布详情页异常, userName:{}, link:{}, e:{}"
						,userName, link, ExceptionUtils.getStackTrace(e));
			return;
		}

		Pattern pattern = Pattern.compile("var msgList = (.*}}]})");
		Matcher matcher = pattern.matcher(detailHtml);
		if (matcher.find()) {
			String json = matcher.group(1);
			JSONObject jsonObject = JSON.parseObject(json);
			JSONArray infos = jsonObject.getJSONArray("list");
			for (int i = 0; i < infos.size(); i++) {
				try {
					JSONObject info = (JSONObject) infos.get(i);
					String sourceId = info.getJSONObject("comm_msg_info").getString("id");
					Long timeStamp = info.getJSONObject("comm_msg_info").getLongValue("datetime");
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date date=null;
					try {
						String str=sdf.format(timeStamp);
						date=sdf.parse(str);
					} catch (Exception e) {
						e.printStackTrace();
					}
					String mainTitle = info.getJSONObject("app_msg_ext_info").getString("title");
					String mainDigest = info.getJSONObject("app_msg_ext_info").getString("digest");
					String mainCover = info.getJSONObject("app_msg_ext_info").getString("cover");
					addArticle(userName, coin, sourceId, timeStamp, date, mainTitle, mainDigest, mainCover);

					JSONArray subInfos = info.getJSONObject("app_msg_ext_info").getJSONArray("multi_app_msg_item_list");
					for (int j = 0; j < subInfos.size(); j++) {
						JSONObject subInfo = (JSONObject) subInfos.get(j);
						String subTitle = subInfo.getString("title");
						String subDigest = subInfo.getString("digest");
						String subCover = subInfo.getString("cover");
						String subSourceId = sourceId + "-" + (j+1);
						addArticle(userName, coin, subSourceId, timeStamp, date, subTitle, subDigest, subCover);
					}
				} catch (Exception e) {
					logger.error("[wechat] 解析json异常，e:{}", ExceptionUtils.getStackTrace(e));
				}
			}
		} else {
			logger.error("[wechat] 解析pub异常 有可能被封禁 userName:{}, url:{}, coin:{}", userName, link, coin.getCnName());
		}

	}

	private void addArticle(String userName, Coin coin, String sourceId, Long timeStamp, Date date, String mainTitle, String mainDigest, String mainCover) {
		Article byContentMd5 = articleService.getByContentMd5(Md5Utils.MD5Encode(mainDigest, "utf-8", true));

		if (byContentMd5 == null) {
			Article article = new Article();
			article.setCoinId(coin.getId());
			article.setUperName(userName);
			article.setTitle(mainTitle);
			article.setContent(mainDigest);
			article.setContentMd5(Md5Utils.MD5Encode(mainDigest, "utf-8", true));
			List<String> imgs = new ArrayList<String>();
			imgs.add(mainCover);
			article.setImages(JSON.toJSONString(imgs));
			article.setSource("wechat");
			article.setSourceId(sourceId);
			article.setPubTime(date);
			article.setPubTimeStamp(timeStamp);
			article.setCreateTime(new Date());
			articleService.add(article);
			logger.info("[wechat] 解析成功，userNam:{}, title:{}", userName, mainTitle);
		} else {
			logger.info("[wechat] 已经入库，userNam:{}, title:{}", userName, mainTitle);
		}
	}

	public List<Map<String, Object>> getUserLinks(String url) {
		List<Map<String, Object>> userLinks = new ArrayList<Map<String, Object>>();

		String listPageHtml;
		try {
			String finalCookie = COOKIE.replace("{CXID}", UUIDGenerator.random32UUID())
					.replace("{SUV}", UUIDGenerator.random32UUID())
					.replace("{SUID}", UUIDGenerator.random32UUID())
					.replace("{SNUID}", UUIDGenerator.random32UUID());
//			listPageHtml = wechatGet(url);
			listPageHtml = CoohuaHttpClient.get(url, "CXID=B26A7741AEFEE3AA7CF2A3FB0BD82618; usid=MFDNYP3iAUT1qUvX; SUV=00039CDD2466D04E598FB7CF89182829; wuid=AAE6E7HyGgAAAAqLK0YmCAIApwM=; SUID=444A227D3765860A5985482E000C7D05; ABTEST=1|1526635549|v1; IPLOC=CN1100; sct=2; ad=qZllllllll2zf$tZlllllV7ydP6lllllhuZnDlllll9llllllW2Wj5@@@@@@@@@@; SNUID=6A1A1014C9CFA76CA470179BCACBE77C; JSESSIONID=aaaCG2YeJwlzYbZHzSknw\n" +
					"Host: weixin.sogou.com");
//			listPageHtml = getByProxy(url, "CXID=B26A7741AEFEE3AA7CF2A3FB0BD82618; usid=MFDNYP3iAUT1qUvX; SUV=00039CDD2466D04E598FB7CF89182829; wuid=AAE6E7HyGgAAAAqLK0YmCAIApwM=; SUID=444A227D3765860A5985482E000C7D05; ABTEST=1|1526635549|v1; IPLOC=CN1100; sct=2; ad=qZllllllll2zf$tZlllllV7ydP6lllllhuZnDlllll9llllllW2Wj5@@@@@@@@@@; SNUID=6A1A1014C9CFA76CA470179BCACBE77C; JSESSIONID=aaaCG2YeJwlzYbZHzSknw\n" +
//					"Host: weixin.sogou.com");
			logger.info("[wechat debug] listPageHtml:{}", listPageHtml );
		} catch (Exception e) {
			logger.error("[wechat] 访问url异常，url:{}", url);
			return userLinks;
		}

		Document listPageDocument = Jsoup.parse(listPageHtml);

		Elements lis = listPageDocument.select("ul.news-list2 li");
		for (Element li : lis) {
			try {
				Element a = li.select("p.tit a").get(0);
				System.out.println(a.text() + "------" + a.attr("href"));
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("name", a.text());
				map.put("link", a.attr("href"));
				userLinks.add(map);
			} catch (Exception e) {
				logger.error("[wechat] 获取用户连接异常 url:{}, e:{}", url, ExceptionUtils.getStackTrace(e));
			}
		}

		return userLinks;
	}



	private static String wechatGet(String url) {
		String finalCookie = COOKIE.replace("{CXID}", UUIDGenerator.random32UUID())
				.replace("{SUV}", UUIDGenerator.random32UUID())
				.replace("{SUID}", UUIDGenerator.random32UUID())
				.replace("{SNUID}", UUIDGenerator.random32UUID());
//		String s = CoohuaHttpClient.get(url, "");
		String s = CoohuaHttpClient.getByProxy(url, finalCookie);
		return s;
	}

	public static void main(String[] args) {

//		String url = "https://mp.weixin.qq.com/profile?src=3&timestamp=1526970890&ver=1&signature=6aLgUTCyiBG*sD8QiUk5DmYXu1eXTQgU3qADL4Y7v*454JcK7JghYU35yb79lN1f-hPGcTqhycUamG6tIM5K3Q==";
//		String url = "http://weixin.sogou.com/weixin?type=1&s_from=input&query=%E6%AF%94%E7%89%B9%E5%B8%81&ie=utf8&_sug_=n&_sug_type_=";
		String url = "https://mp.weixin.qq.com/profile?src=3&timestamp=1527937468&ver=1&signature=XrNh-4pVaOXPQi2O5n8Ww8n0f3J6zOTG-CSX7UL-S0yZKlravhmurYQjNyz0hP94JxZh*DpJmLaVsyCYFJ-o9g==";
		String finalCookie = COOKIE.replace("{CXID}", UUIDGenerator.random32UUID())
				.replace("{SUV}", UUIDGenerator.random32UUID())
				.replace("{SUID}", UUIDGenerator.random32UUID())
				.replace("{SNUID}", UUIDGenerator.random32UUID());

		String html = getByProxy(url, "CXID=B26A7741AEFEE3AA7CF2A3FB0BD82618; usid=MFDNYP3iAUT1qUvX; SUV=00039CDD2466D04E598FB7CF89182829; wuid=AAE6E7HyGgAAAAqLK0YmCAIApwM=; SUID=444A227D3765860A5985482E000C7D05; ABTEST=1|1526635549|v1; IPLOC=CN1100; sct=2; ad=qZllllllll2zf$tZlllllV7ydP6lllllhuZnDlllll9llllllW2Wj5@@@@@@@@@@; SNUID=6A1A1014C9CFA76CA470179BCACBE77C; JSESSIONID=aaaCG2YeJwlzYbZHzSknw\n" +
				"Host: weixin.sogou.com");

//		String s = CoohuaHttpClient.getByProxy(url, finalCookie new HttpHost());

//		System.out.println(s);

//		String s = "AAE6E7HyGgAAAAqLK0YmCAIApwM=";
//		System.out.println(s.length());

//		System.out.println(wechatGet(LIST_URL.replace("{keyword}", "truechain")));

		/*String listPageHtml = null;

		listPageHtml = wechatGet(LIST_URL.replace("{keyword}", "比特币"));

		Document listPageDocument = Jsoup.parse(listPageHtml);

		Elements lis = listPageDocument.select("ul.news-list2 li");
		for (Element li : lis) {
			Element a = li.select("p.tit a").get(0);
			System.out.println(a.text() + "------" + a.attr("href"));
		}*/

		/*String detailHtml = "";
		try {
			detailHtml = wechatGet("https://mp.weixin.qq.com/profile?src=3&timestamp=1526959428&ver=1&signature=6aLgUTCyiBG*sD8QiUk5DmYXu1eXTQgU3qADL4Y7v*454JcK7JghYU35yb79lN1fROjaM3YfVOJOmGAohRIaeg==");
		} catch (Exception e) {
			e.printStackTrace();
			*//*logger.info("[wechat] 获取发布详情页异常, userName:{}, link:{}, e:{}"
					,userName, link, ExceptionUtils.getStackTrace(e));
			return;*//*
		}

		Pattern pattern = Pattern.compile("var msgList = (.*}}]})");
		Matcher matcher = pattern.matcher(detailHtml);
		if (matcher.find()) {
			String json = matcher.group(1);
			JSONObject jsonObject = JSON.parseObject(json);
			JSONArray infos = jsonObject.getJSONArray("list");
			for (int i = 0; i < infos.size(); i++) {
				JSONObject info = (JSONObject) infos.get(i);
				String sourceId = info.getJSONObject("comm_msg_info").getString("id");
				String mainTitle = info.getJSONObject("app_msg_ext_info").getString("title");
				String mainDigest = info.getJSONObject("app_msg_ext_info").getString("digest");
				String mainCover = info.getJSONObject("app_msg_ext_info").getString("cover");
				System.out.println(mainTitle + "---" + mainDigest);

				JSONArray subInfos = info.getJSONObject("app_msg_ext_info").getJSONArray("multi_app_msg_item_list");
				for (int j = 0; j < subInfos.size(); j++) {
					JSONObject subInfo = (JSONObject) subInfos.get(j);
					String subTitle = subInfo.getString("title");
					String subDigest = subInfo.getString("digest");
					String subCover = subInfo.getString("cover");
					System.out.println(subTitle + "---" + subDigest);
				}


			}
		}*/

	}

	public static String getByProxy(String url, String cookie) {
		HttpClient client;

		String value = null;

		for (int i = 0; i < 500; i++) {

//			HttpHost httpHost = HttpHostHolder.httpHost;
			HttpHost httpHost = new HttpHost("45.125.220.177", 8080);
			logger.info("[wechat] 当前使用代理: ip:{}, port:{}", httpHost.getIp(), httpHost.getPort());

			client = CoohuaHttpClient.getProxyHttpClient(httpHost, url);

			HttpConfig config = CoohuaHttpClient.getHttpConfig(url, client, cookie);

			try {

				int code = HttpClientUtil.status(config);

				if (code == 403 || code == 429) {
					logger.info("[wechat] 第 {} 尝试请求页面 当前使用代理被禁止: ip:{}, port:{}", (i + 1), httpHost.getIp(), httpHost.getPort());
					HttpHostHolder.switchProxy();
					continue;
				}

				value = CoohuaHttpClient.doRequest(config);
				if (value.contains("验证码")) {
					logger.info("[wechat] 第 {} 尝试请求页面 当前使用代理访问到验证码页面: ip:{}, port:{}", (i + 1), httpHost.getIp(), httpHost.getPort());
					HttpHostHolder.switchProxy();
					continue;
				}
				if (value.contains("headTrap") && value.contains("tailTrap")) {

				} else {
					logger.info("[wechat debug] 第 {} 尝试页面 页面不是微信页面: ip:{}, port:{}, value:{}", (i + 1), httpHost.getIp(), httpHost.getPort(), value);
					HttpHostHolder.switchProxy();
					continue;
				}

				if (StringUtils.isNotBlank(value)) {
					break;
				} else {
					logger.error("[wechat] 第 {} 尝试页面 当前使用代理返回空值: ip:{}, port:{}", (i + 1), httpHost.getIp(), httpHost.getPort());
					HttpHostHolder.switchProxy();
				}
			} catch (Exception ex) {
				logger.error("[wechat] 第 {} 尝试页面 当前使用代理异常: ip:{}, port:{}, e:{}", (i + 1), httpHost.getIp(), httpHost.getPort(), ex.getMessage());
				HttpHostHolder.switchProxy();
			}
		}

		logger.info("[wechat proxy], url:{}, value:{}", url, value);

		return value;
	}


}
