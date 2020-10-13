package com.css.im.sys.mapper;

import com.css.im.sys.model.SysUserMenu;
import com.css.im.sys.model.SysUserMenuExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserMenuMapper {
    long countByExample(SysUserMenuExample example);

    int deleteByExample(SysUserMenuExample example);

    int deleteByPrimaryKey(String userMenuId);

    int insert(SysUserMenu record);

    int insertSelective(SysUserMenu record);

    List<SysUserMenu> selectByExample(SysUserMenuExample example);

    SysUserMenu selectByPrimaryKey(String userMenuId);

    int updateByExampleSelective(@Param("record") SysUserMenu record, @Param("example") SysUserMenuExample example);

    int updateByExample(@Param("record") SysUserMenu record, @Param("example") SysUserMenuExample example);

    int updateByPrimaryKeySelective(SysUserMenu record);

    int updateByPrimaryKey(SysUserMenu record);
}