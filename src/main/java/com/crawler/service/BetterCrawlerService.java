package com.crawler.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.crawler.dao.model.db.BetterArticle;
import com.crawler.dao.model.db.BetterCoin;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by daiyong on 2018/6/5.
 * email daiyong@qiyi.com
 */
@Service
public class BetterCrawlerService {

	private static final Logger logger = LogManager.getLogger(BetterCrawlerService.class);

	@Autowired
	private BetterArticleService betterArticleService;

	private static final String NEXT_PAGE_URL = "https://weibo.com/p/aj/v6/mblog/mbloglist?ajwvr=6&domain=100505&refer_flag=1001030201_&is_hot=1&pagebar=0&pl_name=Pl_Official_MyProfileFeed__20&script_uri=/u/{oid}&feed_type=0&page=1&pre_page=1&domain_op=100505&__rnd={temstamp}&id={pageId}";

	public void crawler(BetterCoin betterCoin) {
		//爬取微博
		String weiboUrl = betterCoin.getWeiboUrl();
		if (StringUtils.isNotBlank(weiboUrl)) {
			crawlerWeibo(betterCoin);
		} else {
			logger.info("[better crawler] 币 ：{} 没有开通微博", betterCoin.getName());
		}

		//爬取推特
		String twitterUrl = betterCoin.getTwitterUrl();
		if (StringUtils.isNotBlank(twitterUrl)) {
			crawlerTwitter(betterCoin);
		} else {
			logger.info("[better crawler] 币 ：{} 没有开通微博", betterCoin.getName());
		}
	}

	private void crawlerWeibo(BetterCoin betterCoin) {
		logger.info("[better crawler] 币 ：{} 爬取微博开始, url:{}", betterCoin.getName(), betterCoin.getWeiboUrl());

		try {
			String html = CoohuaHttpClient.get(betterCoin.getWeiboUrl(), null);

			Document document = Jsoup.parse(html);
			Elements scripts = document.select("script");
			Iterator<Element> iterator = scripts.iterator();
			while (iterator.hasNext()) {
				String text = iterator.next().html();
	//			if (text.contains("\"ns\":\"pl.content.homeFeed.index\",\"domid\":\"Pl_Official_MyProfileFeed__20\"")) {
				if (text.contains("pl.content.homeFeed.index") && text.contains("Pl_Official_MyProfileFeed__25")) {

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

								BetterArticle byContentMd5 = betterArticleService.getByContentMd5(content_md5);
								if (byContentMd5 == null) {
									BetterArticle article = new BetterArticle();
									article.setCoinId(betterCoin.getId().intValue());
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
									article.setUperName(betterCoin.getName());
									article.setSourceId(null);
									article.setCreateTime(new Date());
									article.setImages(JSON.toJSONString(imageSrcs));
									betterArticleService.add(article);
									logger.info("[微博] 解析成功 币:{}, ", betterCoin.getName());

								} else {
									logger.info("[微博] 已经入库 币:{}", betterCoin.getName());
								}
							} catch (Exception e) {
								logger.error("[微博] 币:{} 解析失败, e:{}", betterCoin.getName(), ExceptionUtils.getStackTrace(e));
							}
						}

						if (webDetails.size() >= 14) {
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

									BetterArticle byContentMd5 = betterArticleService.getByContentMd5(content_md5);
									if (byContentMd5 == null) {
										BetterArticle article = new BetterArticle();
										article.setCoinId(betterCoin.getId().intValue());
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
										article.setUperName(betterCoin.getName());
										article.setSourceId(null);
										article.setCreateTime(new Date());
										article.setImages(JSON.toJSONString(imageSrcs));
										betterArticleService.add(article);
										logger.info("[微博] 解析成功 币:{}, ", betterCoin.getName());

									} else {
										logger.info("[微博] 已经入库 币:{}", betterCoin.getName());
									}
								} catch (Exception e) {
									logger.error("[微博] 币:{} 解析失败, e:{}", betterCoin.getName(), ExceptionUtils.getStackTrace(e));
								}
							}

						}

					}

				}

			}

		} catch (Exception e) {
			logger.error("[微博] 币:{}, 解析异常, e:{}", betterCoin.getName(), ExceptionUtils.getStackTrace(e));
		}

		logger.info("[better crawler] 币 ：{} 爬取微博结束", betterCoin.getName());
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

	private void crawlerTwitter(BetterCoin betterCoin) {

		try {
			String detailHtml = null;
			try {
				detailHtml = CoohuaHttpClient.get(betterCoin.getTwitterUrl(), "");
			} catch (Exception e) {
				logger.error("[twitter] 获取详情页异常 币:{}, detailUtl:{}, e:{}",
						betterCoin.getName(), betterCoin.getTwitterUrl(), ExceptionUtils.getStackTrace(e));
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

						String content = p.html();
						String contentMd5 = Md5Utils.MD5Encode(content, "utf-8", true);
						BetterArticle articleMd5 = betterArticleService.getByContentMd5(contentMd5);
						if (articleMd5 == null) {
							BetterArticle article = new BetterArticle();
							article.setCoinId(betterCoin.getId().intValue());
							article.setUperName(betterCoin.getName());
							article.setTitle(null);
							article.setContent(content);
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
							betterArticleService.add(article);
							logger.info("[twitter] 解析成功 币:{}, content:{}", betterCoin.getName(), content);
						} else {
							logger.error("[twitter] 已经入库 币:{}, content:{}", betterCoin.getName(), content);
						}
					}
				} catch (Exception e) {
					logger.error("[twitter] 解析失败, detailUrl:{}, e:{}", betterCoin.getTwitterUrl(), ExceptionUtils.getStackTrace(e));
				}
			}
		} catch (Exception e) {
			logger.error("[twitter] 解析异常, detailUrl:{}, e:{}", betterCoin.getTwitterUrl(), ExceptionUtils.getStackTrace(e));
		}

	}

}
