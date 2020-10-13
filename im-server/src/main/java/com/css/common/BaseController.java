package com.css.common;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


public abstract class BaseController {

    protected Log logger = LogFactory.getLog(getClass());

    protected String getRootPath(HttpServletRequest request) {
        return request.getContextPath();
    }

    protected String getParentPath(HttpServletRequest request) {
        String rootPath = getRootPath(request);
        rootPath = rootPath.substring(0, rootPath.length() - 1);
        rootPath = rootPath.substring(0,
                rootPath.lastIndexOf(File.separatorChar) + 1);
        return rootPath;
    }

    protected String getWebAppRootPath(HttpServletRequest request) {
        String rootPath = getParentPath(request);
        rootPath = rootPath.substring(0, rootPath.length() - 1);
        rootPath = rootPath.substring(0,
                rootPath.lastIndexOf(File.separatorChar) + 1);
        return rootPath;
    }

    protected int getInt(HttpServletRequest request, String param, int defaultValue) {
        String value = request.getParameter(param);
        return NumberUtils.isNumber(value) ? Integer.parseInt(value) : defaultValue;
    }

    protected Integer getInteger(HttpServletRequest request, String param) {
        String value = request.getParameter(param);
        return NumberUtils.isNumber(value) ? Integer.parseInt(value) : null;
    }

    protected String get(HttpServletRequest request, String param) {
        String value = request.getParameter(param);
        return value != null ? value.trim() : null;
    }

    protected String get(HttpServletRequest request, String param, String defaultValue) {
        String value = request.getParameter(param);
        return StringUtils.isNotBlank(value) ? value.trim() : defaultValue;
    }

    protected String[] getValues(HttpServletRequest request, String param) {
        String[] values = request.getParameterValues(param);
        return values == null ? new String[0] : values;
    }

    protected void sendJSONMessage(HttpServletResponse response, String str) {
        response.setContentType("application/json; charset=" + AppConstant.ENCODE);
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            writer.println(str);
            writer.close();
            response.flushBuffer();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (null != writer) {
                writer.close();
            }
        }

    }

    protected void sendMessage(HttpServletResponse response, String str) {
        response.setContentType("text/html; charset=" + AppConstant.ENCODE);
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            writer.println(str);
            writer.close();
            response.flushBuffer();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (null != writer) {
                writer.close();
            }
        }

    }


    protected void set(HttpServletRequest request, String paramName, Object value) {
        request.setAttribute(paramName, value);
    }

    protected void setNotEmpty(HttpServletRequest request, String paramName, List value) {
        if (value != null && value.size() > 0) {
            request.setAttribute(paramName, value);
        }
    }

    @InitBinder
    protected void initBinder(HttpServletRequest request,
                              ServletRequestDataBinder binder) throws Exception {


    }
}
