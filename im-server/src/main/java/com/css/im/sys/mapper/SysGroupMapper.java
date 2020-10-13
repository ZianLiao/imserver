package com.css.im.sys.mapper;

import com.css.im.sys.model.SysGroup;
import com.css.im.sys.model.SysGroupExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysGroupMapper {
    long countByExample(SysGroupExample example);

    int deleteByExample(SysGroupExample example);

    int deleteByPrimaryKey(String groupId);

    int insert(SysGroup record);

    int insertSelective(SysGroup record);

    List<SysGroup> selectByExampleWithBLOBs(SysGroupExample example);

    List<SysGroup> selectByExample(SysGroupExample example);

    SysGroup selectByPrimaryKey(String groupId);

    int updateByExampleSelective(@Param("record") SysGroup record, @Param("example") SysGroupExample example);

    int updateByExampleWithBLOBs(@Param("record") SysGroup record, @Param("example") SysGroupExample example);

    int updateByExample(@Param("record") SysGroup record, @Param("example") SysGroupExample example);

    int updateByPrimaryKeySelective(SysGroup record);

    int updateByPrimaryKeyWithBLOBs(SysGroup record);

    int updateByPrimaryKey(SysGroup record);
}