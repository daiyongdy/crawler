package com.crawler.dao.mapper.db;

import com.crawler.dao.model.db.JumpGameRecord;
import com.crawler.dao.model.db.JumpGameRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface JumpGameRecordMapper {
    int countByExample(JumpGameRecordExample example);

    int deleteByExample(JumpGameRecordExample example);

    int deleteByPrimaryKey(Long id);

    int insert(JumpGameRecord record);

    int insertSelective(JumpGameRecord record);

    List<JumpGameRecord> selectByExample(JumpGameRecordExample example);

    JumpGameRecord selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") JumpGameRecord record, @Param("example") JumpGameRecordExample example);

    int updateByExample(@Param("record") JumpGameRecord record, @Param("example") JumpGameRecordExample example);

    int updateByPrimaryKeySelective(JumpGameRecord record);

    int updateByPrimaryKey(JumpGameRecord record);
}