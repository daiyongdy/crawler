package com.crawler.service;

import com.crawler.dao.mapper.db.BetterCoinMapper;
import com.crawler.dao.model.db.BetterCoin;
import com.crawler.dao.model.db.BetterCoinExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daiyong on 2018/6/5.
 * email daiyong@qiyi.com
 */
@Service
public class BetterCoinService {


	@Autowired
	private BetterCoinMapper betterCoinMapper;

	public List<BetterCoin> getAllCoin() {
		List<BetterCoin> betterCoins = betterCoinMapper.selectByExample(new BetterCoinExample());
		return betterCoins;
	}

	public List<BetterCoin> getById(long l) {
		BetterCoinExample example = new BetterCoinExample();
		example.createCriteria().andIdEqualTo(l);
		List<BetterCoin> result = new ArrayList<BetterCoin>();
		result.add(betterCoinMapper.selectByPrimaryKey(l));
		return result;
	}
}
