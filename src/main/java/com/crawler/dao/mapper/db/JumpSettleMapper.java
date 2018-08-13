package com.crawler.dao.mapper.db;

import com.crawler.dao.model.db.JumpSettle;
import com.crawler.dao.model.db.JumpSettleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface JumpSettleMapper {
    int countByExample(JumpSettleExample example);

    int deleteByExample(JumpSettleExample example);

    int deleteByPrimaryKey(Long settleId);

    int insert(JumpSettle record);

    int insertSelective(JumpSettle record);

    List<JumpSettle> selectByExample(JumpSettleExample example);

    JumpSettle selectByPrimaryKey(Long settleId);

    int updateByExampleSelective(@Param("record") JumpSettle record, @Param("example") JumpSettleExample example);

    int updateByExample(@Param("record") JumpSettle record, @Param("example") JumpSettleExample example);

    int updateByPrimaryKeySelective(JumpSettle record);

    int updateByPrimaryKey(JumpSettle record);
}