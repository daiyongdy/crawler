package com.crawler.service;

import com.crawler.dao.mapper.db.FeiXiaoHaoCoinDetailMapper;
import com.crawler.dao.model.db.FeiXiaoHaoCoinDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by daiyong on 2018/5/31.
 * email daiyong@qiyi.com
 */
@Service
public class FeiXiaoHaoCoinDetailService {

	@Autowired
	private FeiXiaoHaoCoinDetailMapper feiXiaoHaoCoinDetailMapper;

	public void add(FeiXiaoHaoCoinDetail feiXiaoHaoCoinDetail) {
		feiXiaoHaoCoinDetailMapper.insert(feiXiaoHaoCoinDetail);
	}
}
