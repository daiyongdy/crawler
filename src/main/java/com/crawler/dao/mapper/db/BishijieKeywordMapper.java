package com.crawler.dao.mapper.db;

import com.crawler.dao.model.db.BishijieKeyword;
import com.crawler.dao.model.db.BishijieKeywordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BishijieKeywordMapper {
    int countByExample(BishijieKeywordExample example);

    int deleteByExample(BishijieKeywordExample example);

    int deleteByPrimaryKey(Long id);

    int insert(BishijieKeyword record);

    int insertSelective(BishijieKeyword record);

    List<BishijieKeyword> selectByExample(BishijieKeywordExample example);

    BishijieKeyword selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") BishijieKeyword record, @Param("example") BishijieKeywordExample example);

    int updateByExample(@Param("record") BishijieKeyword record, @Param("example") BishijieKeywordExample example);

    int updateByPrimaryKeySelective(BishijieKeyword record);

    int updateByPrimaryKey(BishijieKeyword record);
}