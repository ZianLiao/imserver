package com.css.im.org.mapper;

import com.css.im.org.model.OrgDept;
import com.css.im.org.model.OrgDeptExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrgDeptMapper {
    long countByExample(OrgDeptExample example);

    int deleteByExample(OrgDeptExample example);

    int deleteByPrimaryKey(String deptId);

    int insert(OrgDept record);

    int insertSelective(OrgDept record);

    List<OrgDept> selectByExample(OrgDeptExample example);

    OrgDept selectByPrimaryKey(String deptId);

    int updateByExampleSelective(@Param("record") OrgDept record, @Param("example") OrgDeptExample example);

    int updateByExample(@Param("record") OrgDept record, @Param("example") OrgDeptExample example);

    int updateByPrimaryKeySelective(OrgDept record);

    int updateByPrimaryKey(OrgDept record);
}