package com.css.im.sys.mapper;

import com.css.im.sys.model.SysGroupMenu;
import com.css.im.sys.model.SysGroupMenuExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysGroupMenuMapper {
    long countByExample(SysGroupMenuExample example);

    int deleteByExample(SysGroupMenuExample example);

    int deleteByPrimaryKey(String groupMenuId);

    int insert(SysGroupMenu record);

    int insertSelective(SysGroupMenu record);

    List<SysGroupMenu> selectByExample(SysGroupMenuExample example);

    SysGroupMenu selectByPrimaryKey(String groupMenuId);

    int updateByExampleSelective(@Param("record") SysGroupMenu record, @Param("example") SysGroupMenuExample example);

    int updateByExample(@Param("record") SysGroupMenu record, @Param("example") SysGroupMenuExample example);

    int updateByPrimaryKeySelective(SysGroupMenu record);

    int updateByPrimaryKey(SysGroupMenu record);
}