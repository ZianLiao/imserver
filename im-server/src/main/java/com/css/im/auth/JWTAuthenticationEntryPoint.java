package com.css.im.auth;

import com.css.common.BaseController;
import com.css.common.ReqResult;
import com.css.common.ResultCode;
import net.sf.json.JSONObject;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

/**
 * Create by wx on 2020-09-07
 */
public class JWTAuthenticationEntryPoint extends BaseController implements AuthenticationEntryPoint, Serializable {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {

        httpServletResponse.setStatus(200);
        httpServletResponse.setCharacterEncoding("utf-8");
        JSONObject obj = JSONObject.fromObject(ReqResult.failed(ResultCode.UNAUTHORIZED));
        sendJSONMessage(httpServletResponse, obj.toString());

    }
}
