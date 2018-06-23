package com.crawler.dao.mapper.db;

import com.crawler.dao.model.db.BishijieArticle;
import com.crawler.dao.model.db.BishijieArticleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BishijieArticleMapper {
    int countByExample(BishijieArticleExample example);

    int deleteByExample(BishijieArticleExample example);

    int deleteByPrimaryKey(Long articleId);

    int insert(BishijieArticle record);

    int insertSelective(BishijieArticle record);

    List<BishijieArticle> selectByExample(BishijieArticleExample example);

    BishijieArticle selectByPrimaryKey(Long articleId);

    int updateByExampleSelective(@Param("record") BishijieArticle record, @Param("example") BishijieArticleExample example);

    int updateByExample(@Param("record") BishijieArticle record, @Param("example") BishijieArticleExample example);

    int updateByPrimaryKeySelective(BishijieArticle record);

    int updateByPrimaryKey(BishijieArticle record);
}