package com.crawler.dao.mapper.db;

import com.crawler.dao.model.db.FeiXiaoHaoCoin;
import com.crawler.dao.model.db.FeiXiaoHaoCoinExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FeiXiaoHaoCoinMapper {
    int countByExample(FeiXiaoHaoCoinExample example);

    int deleteByExample(FeiXiaoHaoCoinExample example);

    int deleteByPrimaryKey(Long id);

    int insert(FeiXiaoHaoCoin record);

    int insertSelective(FeiXiaoHaoCoin record);

    List<FeiXiaoHaoCoin> selectByExample(FeiXiaoHaoCoinExample example);

    FeiXiaoHaoCoin selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") FeiXiaoHaoCoin record, @Param("example") FeiXiaoHaoCoinExample example);

    int updateByExample(@Param("record") FeiXiaoHaoCoin record, @Param("example") FeiXiaoHaoCoinExample example);

    int updateByPrimaryKeySelective(FeiXiaoHaoCoin record);

    int updateByPrimaryKey(FeiXiaoHaoCoin record);
}