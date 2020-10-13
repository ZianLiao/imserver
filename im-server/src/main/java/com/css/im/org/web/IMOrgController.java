package com.css.im.org.web;

import com.css.common.BaseController;
import com.css.common.ReqResult;
import com.css.im.org.service.IMOrgService;
import com.css.im.sys.model.SysUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Create by wx on 2020-09-03
 */
@Api(tags = "组织机构接口")
@RestController
@RequestMapping("/im/api/org")
public class IMOrgController extends BaseController {
    @Autowired
    IMOrgService orgService;

    @ApiOperation(value = "获取组织机构列表")
    @GetMapping(value = "/alldepts")
    @ResponseBody
    private ReqResult getAllDepts(HttpServletRequest request) {
        return ReqResult.success(orgService.getOrgDeptTree());
    }

    @ApiOperation(value = "通过组织机构ID获取组织机构人员")
    @GetMapping(value = "/members")
    @ResponseBody
    private ReqResult getMembersByDept(HttpServletRequest request) {
        String deptId = get(request, "deptId");
        List<SysUser> members = orgService.getMembersByDept(deptId);
        return ReqResult.success(members);
    }


}
