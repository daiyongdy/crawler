package com.crawler.dao.mapper.db;

import com.crawler.dao.model.db.ZombieUser;
import com.crawler.dao.model.db.ZombieUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ZombieUserMapper {
    int countByExample(ZombieUserExample example);

    int deleteByExample(ZombieUserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ZombieUser record);

    int insertSelective(ZombieUser record);

    List<ZombieUser> selectByExample(ZombieUserExample example);

    ZombieUser selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ZombieUser record, @Param("example") ZombieUserExample example);

    int updateByExample(@Param("record") ZombieUser record, @Param("example") ZombieUserExample example);

    int updateByPrimaryKeySelective(ZombieUser record);

    int updateByPrimaryKey(ZombieUser record);
}