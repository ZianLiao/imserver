package com.css.im.sys.mapper;

import com.css.im.sys.model.SysGroupRole;
import com.css.im.sys.model.SysGroupRoleExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysGroupRoleMapper {
    long countByExample(SysGroupRoleExample example);

    int deleteByExample(SysGroupRoleExample example);

    int deleteByPrimaryKey(String groupRoleId);

    int insert(SysGroupRole record);

    int insertSelective(SysGroupRole record);

    List<SysGroupRole> selectByExample(SysGroupRoleExample example);

    SysGroupRole selectByPrimaryKey(String groupRoleId);

    int updateByExampleSelective(@Param("record") SysGroupRole record, @Param("example") SysGroupRoleExample example);

    int updateByExample(@Param("record") SysGroupRole record, @Param("example") SysGroupRoleExample example);

    int updateByPrimaryKeySelective(SysGroupRole record);

    int updateByPrimaryKey(SysGroupRole record);
}