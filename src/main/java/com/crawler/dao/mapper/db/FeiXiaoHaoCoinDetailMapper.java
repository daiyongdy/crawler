package com.crawler.dao.mapper.db;

import com.crawler.dao.model.db.FeiXiaoHaoCoinDetail;
import com.crawler.dao.model.db.FeiXiaoHaoCoinDetailExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FeiXiaoHaoCoinDetailMapper {
    int countByExample(FeiXiaoHaoCoinDetailExample example);

    int deleteByExample(FeiXiaoHaoCoinDetailExample example);

    int deleteByPrimaryKey(Long coinDetailId);

    int insert(FeiXiaoHaoCoinDetail record);

    int insertSelective(FeiXiaoHaoCoinDetail record);

    List<FeiXiaoHaoCoinDetail> selectByExample(FeiXiaoHaoCoinDetailExample example);

    FeiXiaoHaoCoinDetail selectByPrimaryKey(Long coinDetailId);

    int updateByExampleSelective(@Param("record") FeiXiaoHaoCoinDetail record, @Param("example") FeiXiaoHaoCoinDetailExample example);

    int updateByExample(@Param("record") FeiXiaoHaoCoinDetail record, @Param("example") FeiXiaoHaoCoinDetailExample example);

    int updateByPrimaryKeySelective(FeiXiaoHaoCoinDetail record);

    int updateByPrimaryKey(FeiXiaoHaoCoinDetail record);
}