package com.crawler.dao.mapper.db;

import com.crawler.dao.model.db.JumpSettleDetail;
import com.crawler.dao.model.db.JumpSettleDetailExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface JumpSettleDetailMapper {
    int countByExample(JumpSettleDetailExample example);

    int deleteByExample(JumpSettleDetailExample example);

    int deleteByPrimaryKey(Long jumpSettleDetailId);

    int insert(JumpSettleDetail record);

    int insertSelective(JumpSettleDetail record);

    List<JumpSettleDetail> selectByExample(JumpSettleDetailExample example);

    JumpSettleDetail selectByPrimaryKey(Long jumpSettleDetailId);

    int updateByExampleSelective(@Param("record") JumpSettleDetail record, @Param("example") JumpSettleDetailExample example);

    int updateByExample(@Param("record") JumpSettleDetail record, @Param("example") JumpSettleDetailExample example);

    int updateByPrimaryKeySelective(JumpSettleDetail record);

    int updateByPrimaryKey(JumpSettleDetail record);
}