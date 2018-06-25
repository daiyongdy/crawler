package com.crawler.service;

import com.crawler.dao.mapper.biz.BishijieArticleBizMapper;
import com.crawler.dao.mapper.db.BishijieArticleMapper;
import com.crawler.dao.model.db.BishijieArticle;
import com.crawler.dao.model.db.BishijieArticleExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by daiyong on 2018/6/23.
 * email daiyong@qiyi.com
 */
@Service
public class BishijieArticleService {
	@Autowired
	private BishijieArticleMapper bishijieArticleMapper;

	@Autowired
	private BishijieArticleBizMapper bishijieArticleBizMapper;

	public void add(BishijieArticle bishijieArticle) {
		bishijieArticleMapper.insertSelective(bishijieArticle);
	}

	public BishijieArticle getBySourceId(String sourceId) {
		BishijieArticleExample bishijieArticleExample = new BishijieArticleExample();
		bishijieArticleExample.createCriteria().andSourceIdEqualTo(sourceId);
		List<BishijieArticle> bishijieArticles = bishijieArticleMapper.selectByExample(bishijieArticleExample);
		if (bishijieArticles.size() > 0) {
			return bishijieArticles.get(0);
		} else {
			return null;
		}
	}

	public Date getMaxPubTime() {
		BishijieArticle article = bishijieArticleBizMapper.getMaxPubTime();
		return article.getPubTime();
	}

	public List<BishijieArticle> getRelation() {
		BishijieArticleExample bishijieArticleExample = new BishijieArticleExample();
		bishijieArticleExample.createCriteria().andCoinNameIsNotNull();
		return bishijieArticleMapper.selectByExample(bishijieArticleExample);
	}

	public void update(BishijieArticle article) {
		bishijieArticleMapper.updateByPrimaryKeySelective(article);
	}

	public List<BishijieArticle> getAll() {
		return bishijieArticleMapper.selectByExample(new BishijieArticleExample());
	}
}
