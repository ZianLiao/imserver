package com.css.im.sys.mapper;

import com.css.im.sys.model.SysGroupPermission;
import com.css.im.sys.model.SysGroupPermissionExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysGroupPermissionMapper {
    long countByExample(SysGroupPermissionExample example);

    int deleteByExample(SysGroupPermissionExample example);

    int deleteByPrimaryKey(String groupPermissionId);

    int insert(SysGroupPermission record);

    int insertSelective(SysGroupPermission record);

    List<SysGroupPermission> selectByExample(SysGroupPermissionExample example);

    SysGroupPermission selectByPrimaryKey(String groupPermissionId);

    int updateByExampleSelective(@Param("record") SysGroupPermission record, @Param("example") SysGroupPermissionExample example);

    int updateByExample(@Param("record") SysGroupPermission record, @Param("example") SysGroupPermissionExample example);

    int updateByPrimaryKeySelective(SysGroupPermission record);

    int updateByPrimaryKey(SysGroupPermission record);
}