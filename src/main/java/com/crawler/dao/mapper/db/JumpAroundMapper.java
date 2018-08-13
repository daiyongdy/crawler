package com.crawler.dao.mapper.db;

import com.crawler.dao.model.db.JumpAround;
import com.crawler.dao.model.db.JumpAroundExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface JumpAroundMapper {
    int countByExample(JumpAroundExample example);

    int deleteByExample(JumpAroundExample example);

    int deleteByPrimaryKey(String aroundId);

    int insert(JumpAround record);

    int insertSelective(JumpAround record);

    List<JumpAround> selectByExample(JumpAroundExample example);

    JumpAround selectByPrimaryKey(String aroundId);

    int updateByExampleSelective(@Param("record") JumpAround record, @Param("example") JumpAroundExample example);

    int updateByExample(@Param("record") JumpAround record, @Param("example") JumpAroundExample example);

    int updateByPrimaryKeySelective(JumpAround record);

    int updateByPrimaryKey(JumpAround record);
}