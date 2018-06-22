package com.crawler.dao.mapper.db;

import com.crawler.dao.model.db.Coin;
import com.crawler.dao.model.db.CoinExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CoinMapper {
    int countByExample(CoinExample example);

    int deleteByExample(CoinExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Coin record);

    int insertSelective(Coin record);

    List<Coin> selectByExample(CoinExample example);

    Coin selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Coin record, @Param("example") CoinExample example);

    int updateByExample(@Param("record") Coin record, @Param("example") CoinExample example);

    int updateByPrimaryKeySelective(Coin record);

    int updateByPrimaryKey(Coin record);
}