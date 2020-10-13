package com.css.im.org.service.impl;

import com.css.common.BaseService;
import com.css.im.org.mapper.OrgDeptMapper;
import com.css.im.org.mapper.OrgUserDeptMapper;
import com.css.im.org.model.OrgDept;
import com.css.im.org.model.OrgUserDept;
import com.css.im.org.model.OrgUserDeptExample;
import com.css.im.org.service.IMOrgService;
import com.css.im.sys.mapper.SysUserMapper;
import com.css.im.sys.model.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by wx on 2020-09-03
 */
@Service
public class IMOrgServiceImpl extends BaseService implements IMOrgService {

    @Autowired
    OrgDeptMapper orgDeptMapper;
    @Autowired
    SysUserMapper userMapper;
    @Autowired
    OrgUserDeptMapper orgUserDeptMapper;

    List<OrgDept> res = new ArrayList<OrgDept>();

    @Override
    public List<OrgDept> getOrgDeptTree() {
        List<OrgDept> depts = orgDeptMapper.selectByExample(null);
        //构建组织机构树状结构
        for (OrgDept dept : depts) {
            constructTree(depts, dept);
        }
        return res;
    }

    @Override
    public List<SysUser> getMembersByDept(String deptId) {
        return userMapper.getUserListByDept(deptId);
    }

    @Override
    public List<OrgDept> getAllOrg() {
        return orgDeptMapper.selectByExample(null);
    }

    @Override
    public String getOrgByUser(String userId) {
        OrgUserDeptExample filter = new OrgUserDeptExample();
        filter.createCriteria().andUserIdEqualTo(userId);
        List<OrgUserDept> orgUserDepts = orgUserDeptMapper.selectByExample(filter);
        if (orgUserDepts != null && orgUserDepts.size() > 0) {
            return orgUserDepts.get(0).getDeptId();
        }
        return null;
    }

    private void constructTree(List<OrgDept> deptList, OrgDept targetDept) {
        for (OrgDept dept : deptList) {
            if (dept.getFatherId() == null) {
                //top layer
                continue;
            }
            if (dept.getFatherId().equals(targetDept.getDeptId())) {
                targetDept.subDepts.add(dept);
            }
        }
        res.add(targetDept);
    }
}
