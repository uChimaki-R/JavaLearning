package com.pockyr.filter;

import com.pockyr.utils.JWTUtils;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.IOException;

@Slf4j
@WebFilter(urlPatterns = "/*") // 指定拦截的路径格式
public class CheckLoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // 先转为Http
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // 获取请求的url路径判断是否login请求
        String url = request.getRequestURL().toString();
        if(url.contains("login")){
            log.info("登录请求, 直接放行");
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        String token = request.getHeader("token");
        if(!StringUtils.hasLength(token)){
            // token为空不合法，返回提示信息
            log.info("token为空，不合法");
            // 需要自己手写返回的错误信息
            String error = "{'code': 1, 'msg': 'NOT_LOGIN', 'data': null}";
            response.getWriter().write(error);
            return;
        }
        // 判断token合法性
        try{
            JWTUtils.parseJWT(token);
        }catch (Exception e) {
//            e.printStackTrace();
            log.info("token内容解析错误");
            // 需要自己手写返回的错误信息
            String error = "{\"code\": 0, \"msg\": \"NOT_LOGIN\", \"data\": null}";
            response.getWriter().write(error);
            return;
        }
        // token合法，放行
        log.info("token合法，放行");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
