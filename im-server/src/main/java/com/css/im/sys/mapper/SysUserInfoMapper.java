package com.css.im.sys.mapper;

import com.css.im.sys.model.SysUserInfo;
import com.css.im.sys.model.SysUserInfoExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserInfoMapper {
    long countByExample(SysUserInfoExample example);

    int deleteByExample(SysUserInfoExample example);

    int insert(SysUserInfo record);

    int insertSelective(SysUserInfo record);

    List<SysUserInfo> selectByExample(SysUserInfoExample example);

    int updateByExampleSelective(@Param("record") SysUserInfo record, @Param("example") SysUserInfoExample example);

    int updateByExample(@Param("record") SysUserInfo record, @Param("example") SysUserInfoExample example);
}