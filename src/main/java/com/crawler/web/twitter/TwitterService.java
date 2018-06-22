package com.crawler.web.twitter;

import com.alibaba.fastjson.JSON;
import com.crawler.dao.model.db.Article;
import com.crawler.dao.model.db.Coin;
import com.crawler.service.ArticleService;
import com.crawler.util.Md5Utils;
import com.crawler.util.httpclient.CoohuaHttpClient;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
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

/**
 * Created by daiyong on 2018/5/23.
 * email daiyong@qiyi.com
 */
@Service
public class TwitterService {

	private static final Logger logger = LogManager.getLogger(TwitterService.class);

	@Autowired
	private ArticleService articleService;

	private static final String LIST_URL = "https://twitter.com/search?f=users&q={user}&src=typd&lang=en";

	private static final String LIST_COOKIE = "personalization_id=\"v1_w4sjJJyIQ2KOJX11pjmJKg==\"; guest_id=v1%3A150242428810615334; external_referer=padhuUp37zi31AjaRn9aAreGT7U37xuXqa9w%2B3P9erb2IYV%2FMDw%2FaChoROC%2B4Rzs|0|8e8t2xd8A2w%3D; _ga=GA1.2.1451574771.1526635352; _twitter_sess=BAh7CSIKZmxhc2hJQzonQWN0aW9uQ29udHJvbGxlcjo6Rmxhc2g6OkZsYXNo%250ASGFzaHsABjoKQHVzZWR7ADoPY3JlYXRlZF9hdGwrCHjq3opjAToMY3NyZl9p%250AZCIlOTQ4OTJlYjUzNTU0MzE0OWZkMTE0NGM3MWFiYzlmYTk6B2lkIiVlMWNj%250AZDcyMWUwMDhkYzA5ZTRmM2MwNDk1MWVhOTJmMQ%253D%253D--8fdbe4df9ca82022ea8407da1534dcb15ae7472c; lang=en; ct0=acadb03c5bc53d2723902013595676ed; _gid=GA1.2.1033174377.1527043256; __utma=43838368.1451574771.1526635352.1527043267.1527043267.1; __utmc=43838368; __utmz=43838368.1527043267.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); gt=999119115179646976";

	private static final String DETAIL_URL_PREFIX = "https://twitter.com";

	public void crawler(Coin coin) {
		String url = LIST_URL.replace("{user}", coin.getName());
		List<Map<String, String>> userLinks = getUserLinks(url);
		logger.info("[twitter] userlinks， url:{}, userLinks:{}", url, JSON.toJSONString(userLinks));

		for (int i = 0; i < userLinks.size(); i++) {
			Map<String, String> map = userLinks.get(i);
			String userName = map.get("user");
			String link = map.get("link");
			try {
				getPubArticle(userName, coin, link);
				Thread.sleep(5000);
			} catch (Exception e) {
				logger.error("[twitter] 解析detail异常, userName:{}, url:{}, e:{}", userName, link, ExceptionUtils.getStackTrace(e));
			}
		}
	}

	private void getPubArticle(String userName, Coin coin, String detailUrl) {

		String detailHtml = null;
		try {
			detailHtml = CoohuaHttpClient.get(detailUrl, "");
		} catch (Exception e) {
			logger.error("[twitter] 获取详情页异常 userName:{}, detailUtl:{}, e:{}",
							userName, detailUrl, ExceptionUtils.getStackTrace(e));
		}

		Document detailDocument = Jsoup.parse(detailHtml);

		Elements lis = detailDocument.select("li.js-stream-item.stream-item.stream-item");
		for (int i = 0; i < lis.size(); i++) {
			Element p = null;
			Element span = null;
			String imgSrc = null;
			try {
				Element li = lis.get(i);
				Elements div = li.select("div.js-tweet-text-container");
				Elements ps = div.select("p.TweetTextSize.TweetTextSize--normal.js-tweet-text.tweet-text");
				if (ps.size() > 0) {
					p = ps.get(0);
					span = li.select("span._timestamp.js-short-timestamp").get(0);

					Elements imgs = li.select("div.AdaptiveMedia-container img");
					Element img;
					imgSrc = null;
					// 有图片
					if (imgs.size() > 0) {
						img = imgs.get(0);
						imgSrc = img.attr("src");
					}

					String content = p.ownText();
					String contentMd5 = Md5Utils.MD5Encode(content, "utf-8", true);
					Article articleMd5 = articleService.getByContentMd5(contentMd5);
					if (articleMd5 == null) {
						Article article = new Article();
						article.setCoinId(coin.getId());
						article.setUperName(userName);
						article.setTitle(null);
						article.setContent(p.ownText());
						article.setContentMd5(contentMd5);
						List<String> images = new ArrayList<String>();
						if (StringUtils.isNotBlank(imgSrc)) {
							images.add(imgSrc);
						}
						article.setImages(JSON.toJSONString(images));
						article.setSource("twitter");
						article.setSourceId(null);
						Long timeStamp = Long.valueOf(span.attr("data-time")) * 1000;
						SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						Date date=null;
						try {
							String str=sdf.format(timeStamp);
							date=sdf.parse(str);
						} catch (Exception e) {
							e.printStackTrace();
						}
						article.setPubTime(date);
						article.setPubTimeStamp(timeStamp);
						article.setCreateTime(new Date());
						articleService.add(article);
						logger.info("[twitter] 解析成功 userName:{}, content:{}", userName, content);
					} else {
						logger.error("[twitter] 已经入库 userName:{}, content:{}", userName, content);
					}
				}
			} catch (Exception e) {
				logger.error("[twitter] 解析失败, detailUrl:{}, e:{}", detailUrl, ExceptionUtils.getStackTrace(e));
			}
		}
	}

	private static List<Map<String, String>> getUserLinks(String url) {

		List<Map<String, String>> userLinks = new ArrayList<Map<String, String>>();

		String listHtml = null;
		try {
			listHtml = CoohuaHttpClient.get(url, "personalization_id=\"v1_w4sjJJyIQ2KOJX11pjmJKg==\"; guest_id=v1%3A150242428810615334; external_referer=padhuUp37zi31AjaRn9aAreGT7U37xuXqa9w%2B3P9erb2IYV%2FMDw%2FaChoROC%2B4Rzs|0|8e8t2xd8A2w%3D; _ga=GA1.2.1451574771.1526635352; _twitter_sess=BAh7CSIKZmxhc2hJQzonQWN0aW9uQ29udHJvbGxlcjo6Rmxhc2g6OkZsYXNo%250ASGFzaHsABjoKQHVzZWR7ADoPY3JlYXRlZF9hdGwrCHjq3opjAToMY3NyZl9p%250AZCIlOTQ4OTJlYjUzNTU0MzE0OWZkMTE0NGM3MWFiYzlmYTk6B2lkIiVlMWNj%250AZDcyMWUwMDhkYzA5ZTRmM2MwNDk1MWVhOTJmMQ%253D%253D--8fdbe4df9ca82022ea8407da1534dcb15ae7472c; lang=en; ct0=acadb03c5bc53d2723902013595676ed; _gid=GA1.2.1033174377.1527043256; __utma=43838368.1451574771.1526635352.1527043267.1527043267.1; __utmc=43838368; __utmz=43838368.1527043267.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); gt=999154888490631168; _gat=1");
		} catch (Exception e) {
			logger.error("[twitter] 获取用户列表也异常 e:{}", ExceptionUtils.getStackTrace(e));
			return userLinks;
		}

		Document listDocument = Jsoup.parse(listHtml);
		Elements userAs = listDocument.select("a.ProfileCard-avatarLink");
		for (int i = 0; i < userAs.size(); i++) {
			Element userA = userAs.get(i);
			String user = userA.attr("title");
			String link = userA.attr("href");
			Map<String, String> map = new HashMap<String, String>();
			map.put("user", user);
			map.put("link", DETAIL_URL_PREFIX + link);
			userLinks.add(map);
		}

		return userLinks;

	}

	public static void main(String[] args) {
//		String url = "https://twitter.com/search?f=users&q=BTC&src=typd&lang=en";
//		List<Map<String, String>> userLinks = getUserLinks(url);
//		System.out.println(JSON.toJSONString(userLinks));

		String detailHtml = CoohuaHttpClient.get("https://twitter.com/btc", "");
		Document detailDocument = Jsoup.parse(detailHtml);

		Elements lis = detailDocument.select("li.js-stream-item.stream-item.stream-item");
		for (int i = 0; i < lis.size(); i++) {
			Element li = lis.get(i);
			Elements div = li.select("div.js-tweet-text-container");
			Element p = div.select("p.TweetTextSize.TweetTextSize--normal.js-tweet-text.tweet-text").get(0);
			Element span = li.select("span._timestamp.js-short-timestamp").get(0);

			Elements imgs = li.select("div.AdaptiveMedia-container img");
			Element img;
			String imgSrc = "";
			// 有图片
			if (imgs.size() > 0) {
				img = imgs.get(0);
				imgSrc = img.attr("src");
			} else {

			}
			System.out.println("---" + p.ownText() + "--" + span.attr("data-time") + "---" + imgSrc);
		}
	}



}
