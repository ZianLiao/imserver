package com.css.im.auth.web;

import com.css.common.AppConstant;
import com.css.common.BaseController;
import com.css.common.ReqResult;
import com.css.common.ResultCode;
import com.css.im.auth.Audience;
import com.css.im.auth.JwtTokenUtil;
import com.css.im.auth.SecurityFacade;
import com.css.im.cache.service.IMSessionService;
import com.css.im.sys.model.SysUser;
import com.css.im.sys.service.SysUserService;
import com.css.swagger.model.ApiJsonObject;
import com.css.swagger.model.ApiJsonProperty;
import io.swagger.annotations.*;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Create by wx on 2020-09-03
 */
@Api(tags = "登录相关接口")
@RestController
@RequestMapping("/im/api")
public class LoginController extends BaseController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    Audience audience;

    @Autowired
    SysUserService sysUserService;

    @Autowired
    IMSessionService imSessionService;

    /**
     * 登录接口
     */
    @ApiOperation(value = "登录接口")
    @ApiJsonObject(name = "json", value = {
            @ApiJsonProperty(name = "username", description = "用户名", required = true, example = "test"),
            @ApiJsonProperty(name = "password", description = "密码", required = true, example = "123456")
    })
    @ApiImplicitParam(name = "params", required = true, dataType = "json")
    @ApiResponses(value = {
            @ApiResponse(response = ReqResult.class, code = 0, message = "操作成功"),
            @ApiResponse(response = ReqResult.class, code = 1, message = "操作失败"),
            @ApiResponse(response = ReqResult.class, code = 20001, message = "账户名或者密码错误!")
    })
    @PostMapping(value = "/login", produces = AppConstant.MESSAGE_FORMAT_JSON)
    @ResponseBody
    public ReqResult login(@RequestBody JSONObject params, HttpServletRequest request, HttpServletResponse response) throws Exception {

        String username = params.getString("username");
        String password = params.getString("password");
        username = username.trim();
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
        authRequest.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        try {
            // 执行登录认证过程
            Authentication authentication = authenticationManager.authenticate(authRequest);
            // 认证成功存储认证信息到上下文
            SecurityContextHolder.getContext().setAuthentication(authentication);
            SysUser sysUer = (SysUser) authentication.getPrincipal();
            String token = JwtTokenUtil.createJWT(sysUer, audience);
            String tokenStr = JwtTokenUtil.TOKEN_PREFIX + token;
            response.setHeader(JwtTokenUtil.AUTH_HEADER_KEY, tokenStr);

            sysUer.setToken(token);
            sysUserService.updateUser(sysUer);
            return ReqResult.success();
        } catch (Exception e) {
            logger.error(String.format("用户:%s,密码:%s登录失败", username, password), e);
            return ReqResult.failed(ResultCode.LOGIN_ERROR);
        }

    }

    /**
     * 登录接口
     */
    @ApiOperation(value = "退出登录接口", consumes = AppConstant.MESSAGE_FORMAT_JSON, produces = AppConstant.MESSAGE_FORMAT_JSON)
    @ApiResponses(value = {
            @ApiResponse(response = ReqResult.class, code = 0, message = "操作成功"),
            @ApiResponse(response = ReqResult.class, code = 1, message = "操作失败"),
    })
    @GetMapping(value = "/logout", consumes = AppConstant.MESSAGE_FORMAT_JSON, produces = AppConstant.MESSAGE_FORMAT_JSON)
    @ResponseBody
    public ReqResult logout() throws Exception {
        logger.info("退出登录");
        try {
            String userId = SecurityFacade.getCurUserId();
            imSessionService.removeSession(userId);
            SecurityContextHolder.clearContext();
            sysUserService.clearToken(userId);
            return ReqResult.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ReqResult.failed();
        }
    }


}
