package com.crawler.service;

import com.crawler.dao.mapper.db.BishijieKeywordMapper;
import com.crawler.dao.model.db.BishijieKeyword;
import com.crawler.dao.model.db.BishijieKeywordExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by daiyong on 2018/6/22.
 * email daiyong@qiyi.com
 */
@Service
public class BishijieService {

	@Autowired
	private BishijieKeywordMapper bishijieKeywordMapper;

	public void add(BishijieKeyword bishijieKeyword) {
		bishijieKeywordMapper.insert(bishijieKeyword);
	}

	public List<BishijieKeyword> getAll() {
		return bishijieKeywordMapper.selectByExample(new BishijieKeywordExample());
	}

	public static void main(String[] args) {
		System.out.println("daiyongDaihong代勇".toUpperCase());
	}

}
