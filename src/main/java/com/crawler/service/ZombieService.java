package com.crawler.service;

import com.crawler.dao.mapper.biz.ZombieUserBizMapper;
import com.crawler.dao.mapper.db.ZombieUserMapper;
import com.crawler.dao.model.db.ZombieUser;
import com.crawler.dao.model.db.ZombieUserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by daiyong on 2018/5/20.
 * email daiyong@qiyi.com
 */
@Service
public class ZombieService {

	@Autowired
	private ZombieUserMapper zombieUserMapper;

	@Autowired
	private ZombieUserBizMapper zombieUserBizMapper;

	public void add(ZombieUser zombieUser) {
		zombieUserMapper.insert(zombieUser);
	}

	public void deleteDump() {
		List<Map<String, Object>> dumpName = zombieUserBizMapper.getDumpName();
		for (Map<String, Object> stringObjectMap : dumpName) {
			String name = (String) stringObjectMap.get("name");
			ZombieUserExample zombieUserExample = new ZombieUserExample();
			ZombieUserExample.Criteria criteria = zombieUserExample.createCriteria();
			criteria.andNameLike(name);
			List<ZombieUser> zombieUsers = zombieUserMapper.selectByExample(zombieUserExample);
			for (int i = 1; i < zombieUsers.size(); i++) {
				ZombieUser zombieUser = zombieUsers.get(i);
				zombieUserMapper.deleteByPrimaryKey(zombieUser.getId());
			}
		}
	}

}
