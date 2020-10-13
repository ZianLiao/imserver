package com.css.im.sys.web;

import com.alibaba.fastjson.JSON;
import com.css.common.BaseController;
import com.css.common.ReqResult;
import com.css.common.ResultCode;
import com.css.im.auth.SecurityFacade;
import com.css.im.sys.model.SysUser;
import com.css.im.sys.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Create by wx on 2020-09-16
 */
@Api(tags = "用户信息相关接口")
@RestController
@RequestMapping("/im/api/user")
public class SysUserController extends BaseController {

    @Autowired
    SysUserService userService;

    @ApiOperation(value = "获取用户在线状态")
    @GetMapping(value = "/onlineStatus")
    @ResponseBody
    public ReqResult onlineStatus(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String uid = SecurityFacade.getCurUserId();


        return ReqResult.failed();
    }

    @ApiOperation(value = "更改用户在线状态")
    @GetMapping(value = "/changeStatus")
    @ResponseBody
    public ReqResult changeStatus(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String uid = SecurityFacade.getCurUserId();


        return ReqResult.failed();
    }

    @RequestMapping(value = "/getUserInfo", method = RequestMethod.GET)
    private ReqResult getUserInfo(HttpServletRequest request) {
        //通过token获取用户信息, includes all info
        SysUser userInfo = SecurityFacade.getUserProfile();
        SysUser toSend = new SysUser();
        toSend.setName(userInfo.getName());
        toSend.setBirthday(userInfo.getBirthday());
        toSend.setUserId(userInfo.getUserId());
        toSend.setEmail(userInfo.getEmail());
        toSend.setMobile(userInfo.getMobile());
        toSend.setDescription(userInfo.getDescription());
        toSend.setCreater(userInfo.getCreater());
        //TODO need whole path
        toSend.setPhoto(userInfo.getPhoto());
        toSend.setCreateTime(userInfo.getCreateTime());
        toSend.setLastActiveTime(userInfo.getLastActiveTime());
        toSend.setSex(userInfo.getSex());
        toSend.setStatus(userInfo.getStatus());
        return ReqResult.success(toSend);
    }

    @RequestMapping(value = "/modifyUser", method = RequestMethod.POST)
    private ReqResult modifyUser(HttpServletRequest request) {
        String userStr = get(request, "userStr");
        SysUser user = JSON.parseObject(userStr, SysUser.class);
        if (user != null && user.getUserId() != null) {
            boolean flag = userService.updateUser(user);
            if (flag) {
                return ReqResult.success(ResultCode.SUCCESS);
            } else {
                return ReqResult.failed();
            }
        } else {
            return ReqResult.failed();
        }
    }

}
