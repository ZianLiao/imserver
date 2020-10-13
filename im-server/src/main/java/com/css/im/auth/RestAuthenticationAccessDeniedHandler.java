package com.css.im.auth;

import com.css.common.BaseController;
import com.css.common.ReqResult;
import com.css.common.ResultCode;
import net.sf.json.JSONObject;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Create by wx on 2020-09-07
 */
public class RestAuthenticationAccessDeniedHandler extends BaseController implements AccessDeniedHandler {


    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {

        httpServletResponse.setStatus(200);
        httpServletResponse.setCharacterEncoding("utf-8");
        JSONObject obj = JSONObject.fromObject(ReqResult.failed(ResultCode.FORBIDDEN, e.getMessage()));
        sendJSONMessage(httpServletResponse, obj.toString());

    }
}
