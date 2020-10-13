package com.css.im.sys.mapper;

import com.css.im.sys.model.SysPermissionMenu;
import com.css.im.sys.model.SysPermissionMenuExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysPermissionMenuMapper {
    long countByExample(SysPermissionMenuExample example);

    int deleteByExample(SysPermissionMenuExample example);

    int deleteByPrimaryKey(String permissionMenuId);

    int insert(SysPermissionMenu record);

    int insertSelective(SysPermissionMenu record);

    List<SysPermissionMenu> selectByExample(SysPermissionMenuExample example);

    SysPermissionMenu selectByPrimaryKey(String permissionMenuId);

    int updateByExampleSelective(@Param("record") SysPermissionMenu record, @Param("example") SysPermissionMenuExample example);

    int updateByExample(@Param("record") SysPermissionMenu record, @Param("example") SysPermissionMenuExample example);

    int updateByPrimaryKeySelective(SysPermissionMenu record);

    int updateByPrimaryKey(SysPermissionMenu record);
}