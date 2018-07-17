package com.crawler.service;

import com.crawler.dao.mapper.db.CrawlerApiCoinMapper;
import com.crawler.dao.model.db.CrawlerApiCoin;
import com.crawler.dao.model.db.CrawlerApiCoinExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by daiyong on 2018/6/25.
 * email daiyong@qiyi.com
 */
@Service
public class CrawlerApiCoinService {

	@Autowired
	private CrawlerApiCoinMapper crawlerApiCoinMapper;

	public List<CrawlerApiCoin> getAllApiCoin() {
		return crawlerApiCoinMapper.selectByExample(new CrawlerApiCoinExample());
	}

	public void add(CrawlerApiCoin coin) {
		crawlerApiCoinMapper.insert(coin);
	}

	public CrawlerApiCoin getById(long id) {
		return crawlerApiCoinMapper.selectByPrimaryKey(id);
	}
}
