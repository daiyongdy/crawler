package com.crawler.service;

import com.crawler.dao.mapper.db.BetterArticleMapper;
import com.crawler.dao.model.db.BetterArticle;
import com.crawler.dao.model.db.BetterArticleExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by daiyong on 2018/5/21.
 * email daiyong@qiyi.com
 */
@Service
public class BetterArticleService {

	@Autowired
	private BetterArticleMapper betterArticleMapper;

	public void add(BetterArticle article) {
		betterArticleMapper.insert(article);
	}

	public BetterArticle getByContentMd5(String content_md5) {
		BetterArticleExample example = new BetterArticleExample();
		example.createCriteria().andContentMd5EqualTo(content_md5);
		List<BetterArticle> articles = betterArticleMapper.selectByExample(example);
		if (articles == null || articles.size() == 0) {
			return null;
		} else {
			return articles.get(0);
		}
	}


}
