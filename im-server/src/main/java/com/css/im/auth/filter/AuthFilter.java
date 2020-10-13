package com.css.im.auth.filter;

import com.css.im.auth.Audience;
import com.css.im.auth.JwtTokenUtil;
import com.css.im.sys.model.SysUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录成功之后拦截JWT进行鉴权操作
 * Create by wx on 2020-09-01
 */
@Component
public class AuthFilter extends OncePerRequestFilter {

    @Qualifier("sysUserServiceImpl")
    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    Audience audience;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {

        String tokenHeader = request.getHeader(JwtTokenUtil.AUTH_HEADER_KEY);
        String retToken = "";
        if (StringUtils.isBlank(tokenHeader)) {
            retToken = request.getParameter("token");
            if (StringUtils.isNotBlank(retToken)) {
                tokenHeader = JwtTokenUtil.TOKEN_PREFIX + retToken;
            }
        }
        String url = request.getRequestURI();
        String context = request.getContextPath();
        String a = request.getServletPath();
        // 如果请求头中没有Authorization信息则直接放行了
        if (tokenHeader == null || !tokenHeader.startsWith(JwtTokenUtil.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }
        String token = tokenHeader.replace(JwtTokenUtil.TOKEN_PREFIX, "");
        String username = JwtTokenUtil.getUsername(token, audience.getBase64Secret());
        if (username != null && !"".equals(username)) {
            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                SysUser sysUser = (SysUser) userDetailsService.loadUserByUsername(username);
                if (sysUser != null && sysUser.isEnabled() && token.equals(sysUser.getToken())) {
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(sysUser, null, sysUser.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } else {
                SecurityContextHolder.clearContext();
            }
        }
        chain.doFilter(request, response);

    }
}
