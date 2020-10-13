package com.css.im.auth;

import com.css.im.auth.filter.AuthFilter;
import com.css.im.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.DigestUtils;

/**
 * Create by wx on 2020-09-01
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    Audience audience;

    @Autowired
    SysUserService sysUserService;

    @Autowired
    AuthFilter authFilter;

//    @Override
//    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        //auth.authenticationProvider(new JwtAuthenticationProvider(sysUserService));
//    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        //校验用户
        auth.userDetailsService(sysUserService).passwordEncoder(new PasswordEncoder() {
            //对密码进行加密
            @Override
            public String encode(CharSequence charSequence) {
                System.out.println(charSequence.toString());
                return DigestUtils.md5DigestAsHex(charSequence.toString().getBytes());
                //               return charSequence.toString();
            }

            //对密码进行判断匹配
            @Override
            public boolean matches(CharSequence charSequence, String s) {
//                String encode = DigestUtils.md5DigestAsHex(charSequence.toString().getBytes());
//                String srcPwd = s;
//                srcPwd = DigestUtils.md5DigestAsHex(DigestUtils.md5DigestAsHex(s.toString().getBytes()).getBytes());
//                boolean res = srcPwd.equals( encode );
//                return res;

                return s.equals(charSequence.toString());
            }
        });

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // 禁用 csrf, 由于使用的是JWT，我们这里不需要csrf
        http.cors().and().csrf().disable()
                .authorizeRequests() //对请求进行认证
                // 跨域预检请求
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                // 登录URL
                .antMatchers(HttpMethod.POST, "/im/api/login").permitAll()
                .antMatchers(HttpMethod.POST, "/oa/api/**").permitAll()
                //安全认证登录不需要拦截
                .antMatchers(HttpMethod.POST, "/im/api/ca/**").permitAll()
                //      .antMatchers(HttpMethod.POST,"/im/**").permitAll()
                // swagger
                //      .antMatchers("/swagger**/**").permitAll()
                .antMatchers("/index**/**").permitAll()
                //所有请求需要身份认证
                .anyRequest().authenticated()
                .and()
                // 不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling().accessDeniedHandler(new RestAuthenticationAccessDeniedHandler())
                .authenticationEntryPoint(new JWTAuthenticationEntryPoint()); //处理异常信息
        http.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);
        // 退出登录处理器
        // http.logout().logoutUrl("/im/api/logout").logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());
        // 访问控制时登录状态检查过滤器
        //      http.addFilterBefore(new JWTAuthenticationFilter(authenticationManager(),audience), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/doc.html")
                .antMatchers("/swagger-ui.html")
                .antMatchers("/v2/**")
                .antMatchers("/webjars/**")
                .antMatchers("/swagger-resources/**");
    }
}
