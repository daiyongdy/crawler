package com.crawler.dao.mapper.db;

import com.crawler.dao.model.db.BetterCoin;
import com.crawler.dao.model.db.BetterCoinExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BetterCoinMapper {
    int countByExample(BetterCoinExample example);

    int deleteByExample(BetterCoinExample example);

    int deleteByPrimaryKey(Long id);

    int insert(BetterCoin record);

    int insertSelective(BetterCoin record);

    List<BetterCoin> selectByExample(BetterCoinExample example);

    BetterCoin selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") BetterCoin record, @Param("example") BetterCoinExample example);

    int updateByExample(@Param("record") BetterCoin record, @Param("example") BetterCoinExample example);

    int updateByPrimaryKeySelective(BetterCoin record);

    int updateByPrimaryKey(BetterCoin record);
}