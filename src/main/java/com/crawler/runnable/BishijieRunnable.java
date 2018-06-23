package com.crawler.runnable;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.crawler.Constants;
import com.crawler.dao.model.db.BishijieArticle;
import com.crawler.dao.model.db.BishijieKeyword;
import com.crawler.service.BishijieArticleService;
import com.crawler.service.BishijieService;
import com.crawler.util.SpringContextUtil;
import com.crawler.util.httpclient.CoohuaHttpClient;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;
import java.util.List;

/**
 * Created by daiyong on 2018/6/23.
 * email daiyong@qiyi.com
 */
public class BishijieRunnable implements Runnable {

	private static final Logger logger = LogManager.getLogger(BishijieRunnable.class);

	private static final BishijieArticleService BISHIJIE_ARTICLE_SERVICE = SpringContextUtil.getBean("bishijieArticleService");

	private static final String startUrl = "http://www.bishijie.com/api/newsv17/index?size=50&client=pc";

	private static final String nextUrl = "http://www.bishijie.com/api/newsv17/index?size=50&client=pc&timestamp=${time}";

	@Override
	public void run() {

		new Thread(new KeyWordRunnable()).start();

		logger.info("[币世界] 开始抓取");

		while (true) {
			try {
				//获取最大的发布时间
				Date maxPubTime = BISHIJIE_ARTICLE_SERVICE.getMaxPubTime();
				long endTime = maxPubTime.getTime() / 1000;

				long time = add(startUrl);

				while (time > endTime) {
					String url = nextUrl.replace("${time}", String.valueOf(time));
					time = add(url);
				}

				logger.info("[币世界] 线程休眠 等待下一次爬取");
				Thread.sleep(60 * 1000 * 10); //10分钟查询一次

			} catch (Exception e) {
				logger.info("[币世界] 发生异常 e:{}", ExceptionUtils.getStackTrace(e));

			}
		}

	}

	public long add(String url) {

		logger.info("current url:{}", url);

		String firstPage = CoohuaHttpClient.get(url, "");

		JSONObject firstJsonObject = JSON.parseObject(firstPage);

		List<BishijieObjecct> bishijieObjecctList = parse(firstJsonObject);

		logger.info("current size:{}", bishijieObjecctList.size());

		long time = System.currentTimeMillis() / 1000;

		for (BishijieObjecct bishijieObjecct : bishijieObjecctList) {

			time = bishijieObjecct.getTimestamp();

			String sourceId = bishijieObjecct.getSourceId();
			BishijieArticle originArticle = BISHIJIE_ARTICLE_SERVICE.getBySourceId(sourceId);
			if (originArticle == null) {

				logger.info("[币世界] title:{} 不存在, 添加", bishijieObjecct.getTitle());

				BishijieArticle article = new BishijieArticle();
				article.setContent(bishijieObjecct.getContent());
				article.setTitle(bishijieObjecct.getTitle());
				article.setCreateTime(new Date());
				article.setPubTime(new Date(bishijieObjecct.getTimestamp() * 1000));
				article.setCoinName(getCoinName(bishijieObjecct.getTitle()));
				article.setSourceId(bishijieObjecct.getSourceId());
				try {
					BISHIJIE_ARTICLE_SERVICE.add(article);
				} catch (Exception e) {
					logger.error("[币世界] 添加文章异常, e:{}", ExceptionUtils.getStackTrace(e));
				}
			} else {
				logger.info("[币世界] title:{} 已经存在", bishijieObjecct.getTitle());
			}

		}

		return time;
	}

	private String getCoinName(String title) {
		for (String keyword : Constants.KEYWORDS) {
			if (title.toUpperCase().contains(keyword.toUpperCase())) {
				return keyword;
			}
		}

		return null;
	}

	List<BishijieObjecct> parse(JSONObject jsonObject) {
		List<BishijieObjecct> result = Lists.newArrayList();

		JSONArray data = jsonObject.getJSONArray("data");
		JSONObject data_0_object = data.getJSONObject(0);

		JSONArray objects = data_0_object.getJSONArray("buttom");
		if (objects != null) {
			for (int i = 0; i < objects.size(); i++) {
				JSONObject jsonObject1 = objects.getJSONObject(i);
				BishijieObjecct bishijieObjecct = new BishijieObjecct();
				bishijieObjecct.setContent(jsonObject1.getString("content"));
				bishijieObjecct.setTitle(jsonObject1.getString("title"));
				bishijieObjecct.setTimestamp(jsonObject1.getLongValue("issue_time"));
				bishijieObjecct.setSourceId(String.valueOf(jsonObject1.getLongValue("newsflash_id")));
				result.add(bishijieObjecct);
			}
		}

		return result;
	}
}

class BishijieObjecct {
	private String content;
	private Long timestamp;
	private String title;
	private String sourceId;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
}

class KeyWordRunnable implements Runnable {

	private static final Logger logger = LogManager.getLogger(KeyWordRunnable.class);

	private static final BishijieService BISHIJIE_SERVICE = SpringContextUtil.getBean("bishijieService");

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(60 * 1000 * 20);

				logger.info("[币世界] 开始刷新关键词");

				List<BishijieKeyword> keywords = BISHIJIE_SERVICE.getAll();
				for (BishijieKeyword keyword : keywords) {
					if (!Constants.KEYWORDS.contains(keyword.getKeyword())) {
						Constants.KEYWORDS.add(keyword.getKeyword());
					}
				}

				logger.info("[币世界] 刷新关键词完毕，当前关键词数量:{}", Constants.KEYWORDS.size());
			} catch (Exception e) {
				logger.error("[币世界], 更新关键词异常");
			}
		}
	}

}
