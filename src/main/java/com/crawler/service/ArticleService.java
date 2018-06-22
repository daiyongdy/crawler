package com.crawler.service;

import com.crawler.dao.mapper.db.ArticleMapper;
import com.crawler.dao.model.db.Article;
import com.crawler.dao.model.db.ArticleExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by daiyong on 2018/5/21.
 * email daiyong@qiyi.com
 */
@Service
public class ArticleService {

	@Autowired
	private ArticleMapper articleMapper;

	public void add(Article article) {
		articleMapper.insert(article);
	}

	public Article getByContentMd5(String content_md5) {
		ArticleExample example = new ArticleExample();
		example.createCriteria().andContentMd5EqualTo(content_md5);
		List<Article> articles = articleMapper.selectByExample(example);
		if (articles == null || articles.size() == 0) {
			return null;
		} else {
			return articles.get(0);
		}
	}

	public Article getBySourceId(String sourceId) {
		ArticleExample example = new ArticleExample();
		example.createCriteria().andSourceIdEqualTo(sourceId);
		List<Article> articles = articleMapper.selectByExample(example);
		if (articles == null || articles.size() == 0) {
			return null;
		} else {
			return articles.get(0);
		}
	}

}
