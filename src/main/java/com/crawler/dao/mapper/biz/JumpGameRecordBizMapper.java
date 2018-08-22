package com.crawler.dao.mapper.biz;

import com.crawler.dao.model.db.JumpGameRecord;

import java.util.List;

/**
 * Created by daiyong on 2018/8/22.
 * email daiyong@qiyi.com
 */
public interface JumpGameRecordBizMapper {

	List<JumpGameRecord> records(String userId);

}
