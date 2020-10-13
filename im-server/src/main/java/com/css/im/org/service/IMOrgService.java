package com.css.im.org.service;

import com.css.im.org.model.OrgDept;
import com.css.im.sys.model.SysUser;

import java.util.List;

/**
 * Create by wx on 2020-09-03
 */
public interface IMOrgService {

    /**
     * 获取部门组织树
     */
    List<OrgDept> getOrgDeptTree();

    List<SysUser> getMembersByDept(String deptId);

    //for v1 version
    List<OrgDept> getAllOrg();

    String getOrgByUser(String userId);
}
