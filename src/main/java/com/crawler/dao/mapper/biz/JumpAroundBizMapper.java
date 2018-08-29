package com.crawler.dao.mapper.biz;

import com.crawler.dao.model.db.JumpAround;

import java.util.List;

/**
 * Created by daiyong on 2018/8/12.
 * email daiyong@qiyi.com
 */
public interface JumpAroundBizMapper {

	JumpAround lockGet(String aroundId);


	List<JumpAround> getFinishedAround();


	JumpAround getUnFinishedAround(String userId);

	List<JumpAround> getUnFinishedAroundList(String userId);


}
