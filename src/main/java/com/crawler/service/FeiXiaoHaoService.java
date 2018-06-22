package com.crawler.service;

import com.crawler.dao.mapper.db.FeiXiaoHaoCoinMapper;
import com.crawler.dao.model.db.FeiXiaoHaoCoin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by daiyong on 2018/5/31.
 * email daiyong@qiyi.com
 */
@Service
public class FeiXiaoHaoService {

	@Autowired
	private FeiXiaoHaoCoinMapper feiXiaoHaoCoinMapper;

	public void add(FeiXiaoHaoCoin feiXiaoHaoCoin) {
		feiXiaoHaoCoinMapper.insert(feiXiaoHaoCoin);
	}

	public FeiXiaoHaoCoin getById(long l) {
		return feiXiaoHaoCoinMapper.selectByPrimaryKey(l);
	}
}
