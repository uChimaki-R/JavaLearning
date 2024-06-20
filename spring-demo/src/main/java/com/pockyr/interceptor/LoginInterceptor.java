package com.pockyr.interceptor;

import com.pockyr.utils.JWTUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Override // 目标资源方法执行前执行，返回值决定是否放行
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取请求的url路径判断是否login请求
        String url = request.getRequestURL().toString();
        if(url.contains("login")){
            log.info("登录请求, 直接放行");
            return true;
        }
        String token = request.getHeader("token");
        if(!StringUtils.hasLength(token)){
            // token为空不合法，返回提示信息
            log.info("token为空，不合法");
            // 需要自己手写返回的错误信息
            String error = "{\"code\": 0, \"msg\": \"NOT_LOGIN\", \"data\": null}";
            response.getWriter().write(error);
            return false;
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
            return false;
        }
        // token合法，放行
        log.info("token合法，放行");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 目标资源方法执行后执行
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 视图渲染之后执行
    }
}
