package com.crawler.dao.mapper.db;

import com.crawler.dao.model.db.JumpParticipant;
import com.crawler.dao.model.db.JumpParticipantExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface JumpParticipantMapper {
    int countByExample(JumpParticipantExample example);

    int deleteByExample(JumpParticipantExample example);

    int deleteByPrimaryKey(Long participantId);

    int insert(JumpParticipant record);

    int insertSelective(JumpParticipant record);

    List<JumpParticipant> selectByExample(JumpParticipantExample example);

    JumpParticipant selectByPrimaryKey(Long participantId);

    int updateByExampleSelective(@Param("record") JumpParticipant record, @Param("example") JumpParticipantExample example);

    int updateByExample(@Param("record") JumpParticipant record, @Param("example") JumpParticipantExample example);

    int updateByPrimaryKeySelective(JumpParticipant record);

    int updateByPrimaryKey(JumpParticipant record);
}