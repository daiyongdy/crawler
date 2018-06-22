package com.crawler.runnable;

import com.alibaba.fastjson.JSON;
import com.crawler.dao.model.db.Coin;
import com.crawler.pool.IPCrawler;
import com.crawler.pool.IPPool;
import com.crawler.queue.Queue;
import com.crawler.queue.TwitterQueue;
import com.crawler.queue.WechatQueue;
import com.crawler.queue.WeiboQueue;
import com.crawler.service.CoinService;
import com.crawler.util.SpringContextUtil;
import com.crawler.web.twitter.TwitterService;
import com.crawler.web.wechat.WechatService;
import com.crawler.web.weibo.WeiboService;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 *
 * @author daiyong
 * @date 2018/5/21
 * email daiyong@qiyi.com
 * 没有代理 所以采用单线程爬取 否则有可能被封ip
 */
public class CrawlerRunnable implements Runnable{
	
	private static final Logger logger = LogManager.getLogger(CrawlerRunnable.class);

	private static final CoinService coinService = SpringContextUtil.getBean("coinService");

	private static final WeiboService weiboService = SpringContextUtil.getBean("weiboService");

	private static final WechatService wechatService = SpringContextUtil.getBean("wechatService");

	private static final TwitterService twitterService = SpringContextUtil.getBean("twitterService");

	private Queue WEIBO_QUEUE = new WeiboQueue();
	private Queue WEICHAT_QUEUE = new WechatQueue();
	private Queue Twitter_QUEUE = new TwitterQueue();

	@Override
	public void run() {

		//ip爬取完毕 再向下执行
		IPCrawler.crawlerIps();

		System.out.println(IPPool.size());

		//启动微博消费者
		new WeiboConsumer().start();

		//启动微信消费者
		new WechatConsumer().start();

		//启动推特消费者
		new TwitterConsumer().start();

		while (true) {
			try {

				List<Coin> allCoin = coinService.getAllCoin();

				//遍历所有的币
				for (Coin coin : allCoin) {
					//加入微博队列
					WEIBO_QUEUE.put(coin);
					logger.info("币:{} 加入微博队列", JSON.toJSONString(coin));

					WEICHAT_QUEUE.put(coin);
					logger.info("币:{} 加入微信队列", JSON.toJSONString(coin));

					Twitter_QUEUE.put(coin);
					logger.info("币:{} 加入推特队列", JSON.toJSONString(coin));

					try {
						Thread.sleep(60 * 1000 * 1);
					} catch (InterruptedException e) {
						logger.error("休眠被打断, e:{}", ExceptionUtils.getStackTrace(e));
					}
				}

			} catch (Exception e) {
				logger.error("本次爬取失败, e:{}", ExceptionUtils.getStackTrace(e));
			}
		}


	}

	//微博消费者
	class WeiboConsumer {
		public void start() {
			new Thread(new Runnable() {
				@Override
				public void run() {
					while (true) {
						Coin coin = null;
						try {
							coin = (Coin) WEIBO_QUEUE.get();
							logger.info("[微博] 币：{}开始抓取", JSON.toJSONString(coin));
							weiboService.crawler(coin);
							logger.info("[微博] 币：{}开始完毕", JSON.toJSONString(coin));
						} catch (Exception e) {
							logger.error("[微博] 币：{} 抓取异常, e:{}", JSON.toJSONString(coin), ExceptionUtils.getStackTrace(e));
						}
					}
				}
			}).start();
		}
	}

	//微博消费者
	class WechatConsumer {
		public void start() {
			new Thread(new Runnable() {
				@Override
				public void run() {
					while (true) {
						Coin coin = null;
						try {
							coin = (Coin) WEICHAT_QUEUE.get();
							logger.info("[wechat] 币：{}开始抓取", JSON.toJSONString(coin));
							wechatService.crawler(coin);
							logger.info("[wechat] 币：{}开始完毕", JSON.toJSONString(coin));
						} catch (Exception e) {
							logger.error("[wechat] 币：{} 抓取异常, e:{}", JSON.toJSONString(coin), ExceptionUtils.getStackTrace(e));
						}
					}
				}
			}).start();
		}
	}

	//推特消费者
	class TwitterConsumer {
		public void start() {
			new Thread(new Runnable() {
				@Override
				public void run() {
					while (true) {
						Coin coin = null;
						try {
							coin = (Coin) Twitter_QUEUE.get();
							logger.info("[twitter] 币：{}开始抓取", JSON.toJSONString(coin));
							twitterService.crawler(coin);
							logger.info("[twitter] 币：{}开始完毕", JSON.toJSONString(coin));
						} catch (Exception e) {
							logger.error("[twitter] 币：{} 抓取异常, e:{}", JSON.toJSONString(coin), ExceptionUtils.getStackTrace(e));
						}
					}
				}
			}).start();
		}
	}

}
