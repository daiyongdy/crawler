package com.crawler.web.weibo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.crawler.dao.model.db.Article;
import com.crawler.dao.model.db.Coin;
import com.crawler.service.ArticleService;
import com.crawler.util.Md5Utils;
import com.crawler.util.httpclient.CoohuaHttpClient;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by daiyong on 2018/5/21.
 * email daiyong@qiyi.com
 */
@Service
public class WeiboService {

	private static final Logger logger = LogManager.getLogger(WeiboService.class);

	private static final String SEARCH_URL = "http://s.weibo.com/user/{keyword}?topnav=1&wvr=6&topsug=1";

	private static final String NEXT_PAGE_URL = "https://weibo.com/p/aj/v6/mblog/mbloglist?ajwvr=6&domain=100505&refer_flag=1001030201_&is_hot=1&pagebar=0&pl_name=Pl_Official_MyProfileFeed__20&script_uri=/u/{oid}&feed_type=0&page=1&pre_page=1&domain_op=100505&__rnd={temstamp}&id={pageId}";

	@Autowired
	private ArticleService articleService;

	public void crawler(Coin coin) {
		String url = SEARCH_URL.replace("{keyword}", coin.getCnName());
		logger.info("[微博] 当前列表页url：{}", url);

		List<Map<String, String>> userLinks = getUserLinks(url);
		logger.info("[微博] 当前列表页url: {}, 结果：{}", url, JSON.toJSONString(userLinks));

		for (Map<String, String> userLink : userLinks) {
			String user = userLink.get("user");
			String link = userLink.get("link");
			String detailUrl = "https://www." + link.replace("//", "");
			try {
				getPubArticle(user, detailUrl, coin);
			} catch (Exception e) {
				logger.error("[微博] 获取详情页异常, user:{}, detailUrl:{}, e:{}",
						user, detailUrl, ExceptionUtils.getStackTrace(e));
			}
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
//		String url = "http://s.weibo.com/user/比特币?topnav=1&wvr=6&topsug=1";
//		getUserLinks(url);
		String url = "https://weibo.com/u/5882225118?refer_flag=1001030201_&is_hot=1";
//		getPubArticle(null, null, url, null);

	}


	private void getPubArticle(String userName, String url, Coin coin) {
		String html = CoohuaHttpClient.get(url, null);

		Document document = Jsoup.parse(html);
		Elements scripts = document.select("script");
		Iterator<Element> iterator = scripts.iterator();
		while (iterator.hasNext()) {
			String text = iterator.next().html();
//			if (text.contains("\"ns\":\"pl.content.homeFeed.index\",\"domid\":\"Pl_Official_MyProfileFeed__20\"")) {
			if (text.contains("pl.content.homeFeed.index") && text.contains("Pl_Official_MyProfileFeed__20")) {

				Pattern pattern = Pattern.compile("(\\{.*\\})");
				Matcher matcher = pattern.matcher(text);
				if (matcher.find()) {
					String json = matcher.group(1);
					JSONObject jsonObject = JSON.parseObject(json);
					String html1 = jsonObject.getString("html");
					Document document1 = Jsoup.parse(html1);

					Elements webDetails = document1.select("div.WB_feed_detail");
					for (Element webDetail : webDetails) {
						try {

							//图片
							Elements images = webDetail.select("div.WB_media_wrap.clearfix img");
							List<String> imageSrcs = new ArrayList<String>();
							for (Element image : images) {
								String src = image.attr("src");
								if (!src.startsWith("https:")) {
									src = "https:" + src;
								}
								imageSrcs.add(src);
							}

							Element timeA = webDetail.select("a.S_txt2").first();
							String time = timeA.attr("title");

							String timeStamp = timeA.attr("date");

							Elements contentDiv = webDetail.select("div.WB_text.W_f14");
							String content = contentDiv.html();
							String content_md5 = Md5Utils.MD5Encode(content, "utf-8", true);

							Article byContentMd5 = articleService.getByContentMd5(content_md5);
							if (byContentMd5 == null) {
								Article article = new Article();
								article.setCoinId(coin.getId());
								article.setContent(content);
								article.setContentMd5(content_md5);
								SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
								try {
									article.setPubTime(simpleDateFormat.parse(time));
								} catch (ParseException e) {
									e.printStackTrace();
								}
								article.setPubTimeStamp(Long.valueOf(timeStamp));
								article.setSource("weibo");
								article.setUperName(userName);
								article.setSourceId(null);
								article.setCreateTime(new Date());
								article.setImages(JSON.toJSONString(imageSrcs));
								articleService.add(article);
								logger.info("[微博] 解析成功 userName:{}, articleId:{}", userName, article.getId());

							} else {
								logger.info("[微博] 已经入库 userName:{}, content:{}", userName, content);
							}
						} catch (Exception e) {
							logger.error("[微博] userName:{} 解析失败, e:{}", userName, ExceptionUtils.getStackTrace(e));
						}
					}

					//微博每页15条 查找下一页
					if (webDetails.size() == 15) {
						String pageId = getPageId(html);
						String oid = getOId(html);
						Long timeStamp = System.currentTimeMillis();
						String finalNextPageUrl = NEXT_PAGE_URL.replace("{oid}", oid).replace("{temstamp}", timeStamp.toString()).replace("{pageId}", pageId);

						String nextPageJson = CoohuaHttpClient.get(finalNextPageUrl, null);
						JSONObject nextPageJsonObject = JSON.parseObject(nextPageJson);
						String pageHtml = nextPageJsonObject.getString("data");

						Document documentNextPage = Jsoup.parse(pageHtml);
						Elements nextPageWebDetails = documentNextPage.select("div.WB_feed_detail");

						for (Element webDetail : nextPageWebDetails) {
							try {

								//图片
								Elements images = webDetail.select("div.WB_media_wrap.clearfix img");
								List<String> imageSrcs = new ArrayList<String>();
								for (Element image : images) {
									String src = image.attr("src");
									if (!src.startsWith("https:")) {
										src = "https:" + src;
									}
									imageSrcs.add(src);
								}

								Element timeA = webDetail.select("a.S_txt2").first();
								String time = timeA.attr("title");

								String date = timeA.attr("date");

								Elements contentDiv = webDetail.select("div.WB_text.W_f14");
								String content = contentDiv.html();
								String content_md5 = Md5Utils.MD5Encode(content, "utf-8", true);

								Article byContentMd5 = articleService.getByContentMd5(content_md5);
								if (byContentMd5 == null) {
									Article article = new Article();
									article.setCoinId(coin.getId());
									article.setContent(content);
									article.setContentMd5(content_md5);
									SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
									try {
										article.setPubTime(simpleDateFormat.parse(time));
									} catch (ParseException e) {
										e.printStackTrace();
									}
									article.setPubTimeStamp(Long.valueOf(date));
									article.setSource("weibo");
									article.setUperName(userName);
									article.setSourceId(null);
									article.setCreateTime(new Date());
									article.setImages(JSON.toJSONString(imageSrcs));
									articleService.add(article);
									logger.info("[微博] 解析成功 userName:{}, articleId:{}", userName, article.getId());

								}else {
									logger.info("[微博] 已经入库 userName:{}, content:{}", userName, content);
								}
							} catch (Exception e) {
								logger.error("[微博] userName:{} 解析失败, e:{}", userName, ExceptionUtils.getStackTrace(e));
							}
						}

					}
				}
			}
		}
	}

	public String getPageId(String html) {

		String result = null;
		Pattern pattern = Pattern.compile("page_id']='(.*)';");
		Matcher matcher = pattern.matcher(html);
		if (matcher.find()) {
			result = matcher.group(1);
		}

		return result;
	}
	public String getOId(String html) {

		String result = null;
		Pattern pattern = Pattern.compile("oid']='(.*)';");
		Matcher matcher = pattern.matcher(html);
		if (matcher.find()) {
			result = matcher.group(1);
		}

		return result;
	}

	/**
	 * 获取用户姓名和列表页链接
	 * @return
	 */
	private static List<Map<String, String>> getUserLinks(String url) {

		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		String html = null;
		try {
			html = CoohuaHttpClient.get(url, null);
		} catch (Exception e) {
			logger.error("[微博] 当前列表页url:{} 爬取异常", url);
			return result;
		}


		Document document = Jsoup.parse(html);
		Elements scripts = document.select("script");
		Iterator<Element> iterator = scripts.iterator();
		while (iterator.hasNext()) {
			try {
				Element script = iterator.next();
				String text = script.html();
				if (text.contains("W_texta W_fb")) {
					Pattern pattern = Pattern.compile("^.*(\\{.*\\}).*$");
					Matcher matcher = pattern.matcher(text);
					if (matcher.find()) {
						String json = matcher.group(1);
						JSONObject jsonObject = JSON.parseObject(json);
						String userHtml = jsonObject.getString("html");
						Document userDocument = Jsoup.parse(userHtml);
						Elements as = userDocument.select("a.W_texta.W_fb");
						Iterator<Element> asIterator = as.iterator();
						while (asIterator.hasNext()) {
							Element a = asIterator.next();
							Map<String, String> map = new HashMap<String, String>();
							map.put("user", a.attr("title"));
							map.put("link", a.attr("href"));
							result.add(map);
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return result;
	}


}
