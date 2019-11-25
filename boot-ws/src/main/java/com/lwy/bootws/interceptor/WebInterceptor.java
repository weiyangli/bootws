package com.lwy.bootws.interceptor;

import com.lwy.bootws.utils.Jwt;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WebInterceptor implements HandlerInterceptor {
    /**
     * 在请求处理之前进行调用（Controller方法调用之前）
     *
     */
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {
        System.out.println("拦截请求---");
        if (true) {
            // 校验 cookie 中 token 信息
            Cookie[] cookies = httpServletRequest.getCookies();
            String token = "";
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("lwy")) {
                    token = cookie.getValue();
                }
            }
            if (StringUtils.isNoneBlank(token) && Jwt.checkToken("lwy", "boot-ws")) {

            } else {
                httpServletResponse.sendRedirect("/");
            }
        }
        return false;
    }

}
