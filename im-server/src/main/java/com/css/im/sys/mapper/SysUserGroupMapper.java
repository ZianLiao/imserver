package com.css.im.sys.mapper;

import com.css.im.sys.model.SysUserGroup;
import com.css.im.sys.model.SysUserGroupExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserGroupMapper {
    long countByExample(SysUserGroupExample example);

    int deleteByExample(SysUserGroupExample example);

    int deleteByPrimaryKey(String userGroupId);

    int insert(SysUserGroup record);

    int insertSelective(SysUserGroup record);

    List<SysUserGroup> selectByExample(SysUserGroupExample example);

    SysUserGroup selectByPrimaryKey(String userGroupId);

    int updateByExampleSelective(@Param("record") SysUserGroup record, @Param("example") SysUserGroupExample example);

    int updateByExample(@Param("record") SysUserGroup record, @Param("example") SysUserGroupExample example);

    int updateByPrimaryKeySelective(SysUserGroup record);

    int updateByPrimaryKey(SysUserGroup record);
}