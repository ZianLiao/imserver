package com.css.im.auth.web;

import com.css.common.AppConstant;
import com.css.common.BaseController;
import com.css.common.ReqResult;
import com.css.common.ResultCode;
import com.css.im.auth.Audience;
import com.css.im.auth.JwtTokenUtil;
import com.css.im.cache.service.IMSessionService;
import com.css.im.log.LogDebug;
import com.css.im.sys.model.SysUser;
import com.css.im.sys.service.SysUserService;
import com.css.utils.StringUtils;
import com.sdt.NetUserAttr.NetUserAttr;
import io.swagger.annotations.Api;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

/**
 * created by Charles Zhang
 *
 * @date 2020/9/28
 */
@Api(tags = "CA登录相关接口")
@RestController
@RequestMapping("/im/api/ca")
public class CALoginController extends BaseController implements InitializingBean {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    Audience audience;

    @Autowired
    SysUserService sysUserService;

    @Autowired
    IMSessionService imSessionService;

    @Value("CA.switch")
    private String testSwitch;

    private static Vector<SysUser> allUserVector = new Vector<SysUser>();

    @LogDebug
    @PostMapping(value = "/login", produces = AppConstant.MESSAGE_FORMAT_JSON)
    @ResponseBody
    public ReqResult login(@RequestBody JSONObject params, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String clientIp = params.getString("clientIp");
        //TODO 去第三方CA认证返回username
        String username = "";
        String password = "";

        if (testSwitch.equals("true")) {
            synchronized (allUserVector){
                if(allUserVector.size() >0) {
                    username = allUserVector.get(0).getUsername();
                    password = allUserVector.get(0).getPassword();
                    allUserVector.remove(0);
                }
            }
        }

        if (StringUtils.isEmpty(username)) {
            return ReqResult.failed();
        }
        UsernamePasswordAuthenticationToken authRequest =
                new UsernamePasswordAuthenticationToken(username, password);
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
            JSONObject returnObj = new JSONObject();
            returnObj.put("realName", sysUer.getName());
            returnObj.put("username", sysUer.getUsername());
            returnObj.put("password", sysUer.getPassword());
            return ReqResult.success(returnObj);
        } catch (Exception e) {
            logger.error(String.format("用户:%s 登录失败", username), e);
            return ReqResult.failed(ResultCode.LOGIN_ERROR);
        }

    }

//    private String getCAUserID(String terminalIP) {
//        try {
//            NetUserAttr userObj = new NetUserAttr(appServerName, appServerPort, terminalIP);
//            userObj.setIP(CAServerIP);
//            userObj.setPort(CAServerPort);
//            userObj.setServName(CAServerName);
//            int result = userObj.getAttrCollection();
//            String alias = userObj.getAttribute("alias");
//            logger.debug("认证 alias " + alias);
//            //if(0==result){
//            //log.debug("即时通讯服务调用安全认证服务成功。用户alias："+userObj.getAttribute("alias")+" 姓名："+userObj.getAttribute("UI_NAME"));
//            return alias;
//            // }
//        } catch (Exception e) {
//            logger.error("即时通讯服务调用安全认证服务失败。");
//            e.printStackTrace();
//        }
//        return null;
//    }

    @Override
    public void afterPropertiesSet() throws Exception {
        List<SysUser> allUserList = sysUserService.getAllUser();
        allUserVector.addAll(allUserList);
    }

}
