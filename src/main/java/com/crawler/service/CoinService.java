package com.crawler.service;

import com.crawler.dao.mapper.db.CoinMapper;
import com.crawler.dao.model.db.Coin;
import com.crawler.dao.model.db.CoinExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by daiyong on 2018/5/21.
 * email daiyong@qiyi.com
 */
@Service
public class CoinService {

	@Autowired
	private CoinMapper coinMapper;

	public List<Coin> getAllCoin() {
		List<Coin> coins = coinMapper.selectByExample(new CoinExample());
		return coins;
	}

}
