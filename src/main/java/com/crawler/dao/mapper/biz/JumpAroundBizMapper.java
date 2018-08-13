package com.crawler.dao.mapper.biz;

import com.crawler.dao.model.db.JumpAround;

/**
 * Created by daiyong on 2018/8/12.
 * email daiyong@qiyi.com
 */
public interface JumpAroundBizMapper {

	public JumpAround lockGet(String aroundId);

}
