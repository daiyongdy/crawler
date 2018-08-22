package com.crawler.service;

import com.crawler.dao.mapper.biz.JumpGameRecordBizMapper;
import com.crawler.dao.model.db.JumpGameRecord;
import com.crawler.model.WebUserDTO;
import com.crawler.model.WebUserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by daiyong on 2018/8/22.
 * email daiyong@qiyi.com
 */
@Service
@Transactional
public class UserService {

	@Autowired
	private JumpGameRecordBizMapper gameRecordBizMapper;

	public List<JumpGameRecord> records() {
		WebUserDTO user = WebUserHolder.getUser();
		return gameRecordBizMapper.records(user.getUserId());
	}

}
