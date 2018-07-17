package com.crawler.dao.mapper.db;

import com.crawler.dao.model.db.CrawlerApiCoin;
import com.crawler.dao.model.db.CrawlerApiCoinExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CrawlerApiCoinMapper {
    int countByExample(CrawlerApiCoinExample example);

    int deleteByExample(CrawlerApiCoinExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CrawlerApiCoin record);

    int insertSelective(CrawlerApiCoin record);

    List<CrawlerApiCoin> selectByExample(CrawlerApiCoinExample example);

    CrawlerApiCoin selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CrawlerApiCoin record, @Param("example") CrawlerApiCoinExample example);

    int updateByExample(@Param("record") CrawlerApiCoin record, @Param("example") CrawlerApiCoinExample example);

    int updateByPrimaryKeySelective(CrawlerApiCoin record);

    int updateByPrimaryKey(CrawlerApiCoin record);
}