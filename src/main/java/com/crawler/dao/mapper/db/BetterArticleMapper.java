package com.crawler.dao.mapper.db;

import com.crawler.dao.model.db.BetterArticle;
import com.crawler.dao.model.db.BetterArticleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BetterArticleMapper {
    int countByExample(BetterArticleExample example);

    int deleteByExample(BetterArticleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BetterArticle record);

    int insertSelective(BetterArticle record);

    List<BetterArticle> selectByExample(BetterArticleExample example);

    BetterArticle selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BetterArticle record, @Param("example") BetterArticleExample example);

    int updateByExample(@Param("record") BetterArticle record, @Param("example") BetterArticleExample example);

    int updateByPrimaryKeySelective(BetterArticle record);

    int updateByPrimaryKey(BetterArticle record);
}