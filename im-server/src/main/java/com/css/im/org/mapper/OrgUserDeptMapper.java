package com.css.im.org.mapper;

import com.css.im.org.model.OrgUserDept;
import com.css.im.org.model.OrgUserDeptExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrgUserDeptMapper {
    long countByExample(OrgUserDeptExample example);

    int deleteByExample(OrgUserDeptExample example);

    int deleteByPrimaryKey(String userDpetId);

    int insert(OrgUserDept record);

    int insertSelective(OrgUserDept record);

    List<OrgUserDept> selectByExample(OrgUserDeptExample example);

    OrgUserDept selectByPrimaryKey(String userDpetId);

    int updateByExampleSelective(@Param("record") OrgUserDept record, @Param("example") OrgUserDeptExample example);

    int updateByExample(@Param("record") OrgUserDept record, @Param("example") OrgUserDeptExample example);

    int updateByPrimaryKeySelective(OrgUserDept record);

    int updateByPrimaryKey(OrgUserDept record);
}