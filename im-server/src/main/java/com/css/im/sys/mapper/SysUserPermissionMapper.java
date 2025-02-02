package com.css.im.sys.mapper;

import com.css.im.sys.model.SysUserPermission;
import com.css.im.sys.model.SysUserPermissionExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserPermissionMapper {
    long countByExample(SysUserPermissionExample example);

    int deleteByExample(SysUserPermissionExample example);

    int deleteByPrimaryKey(String userPermissionId);

    int insert(SysUserPermission record);

    int insertSelective(SysUserPermission record);

    List<SysUserPermission> selectByExample(SysUserPermissionExample example);

    SysUserPermission selectByPrimaryKey(String userPermissionId);

    int updateByExampleSelective(@Param("record") SysUserPermission record, @Param("example") SysUserPermissionExample example);

    int updateByExample(@Param("record") SysUserPermission record, @Param("example") SysUserPermissionExample example);

    int updateByPrimaryKeySelective(SysUserPermission record);

    int updateByPrimaryKey(SysUserPermission record);
}