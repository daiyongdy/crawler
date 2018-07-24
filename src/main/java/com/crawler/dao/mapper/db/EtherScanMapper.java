package com.crawler.dao.mapper.db;

import com.crawler.dao.model.db.EtherScan;
import com.crawler.dao.model.db.EtherScanExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EtherScanMapper {
    int countByExample(EtherScanExample example);

    int deleteByExample(EtherScanExample example);

    int deleteByPrimaryKey(String id);

    int insert(EtherScan record);

    int insertSelective(EtherScan record);

    List<EtherScan> selectByExample(EtherScanExample example);

    EtherScan selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") EtherScan record, @Param("example") EtherScanExample example);

    int updateByExample(@Param("record") EtherScan record, @Param("example") EtherScanExample example);

    int updateByPrimaryKeySelective(EtherScan record);

    int updateByPrimaryKey(EtherScan record);
}