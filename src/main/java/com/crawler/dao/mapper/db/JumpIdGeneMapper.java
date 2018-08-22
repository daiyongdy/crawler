package com.crawler.dao.mapper.db;

import com.crawler.dao.model.db.JumpIdGene;
import com.crawler.dao.model.db.JumpIdGeneExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface JumpIdGeneMapper {
    int countByExample(JumpIdGeneExample example);

    int deleteByExample(JumpIdGeneExample example);

    int deleteByPrimaryKey(Long id);

    int insert(JumpIdGene record);

    int insertSelective(JumpIdGene record);

    List<JumpIdGene> selectByExample(JumpIdGeneExample example);

    int updateByExampleSelective(@Param("record") JumpIdGene record, @Param("example") JumpIdGeneExample example);

    int updateByExample(@Param("record") JumpIdGene record, @Param("example") JumpIdGeneExample example);
}