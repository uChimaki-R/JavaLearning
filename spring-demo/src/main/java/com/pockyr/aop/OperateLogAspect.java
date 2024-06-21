package com.pockyr.aop;

import com.pockyr.mapper.OperateLogMapper;
import com.pockyr.pojo.OperateLog;
import com.pockyr.utils.JWTUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Slf4j
@Aspect
public class OperateLogAspect {
    @Autowired // 使用自动注入的HttpServletRequest可以获取到当前方法所处的请求
    private HttpServletRequest httpServletRequest;

    @Autowired
    private OperateLogMapper operateLogMapper;

    @Pointcut("@annotation(com.pockyr.annotation.OperateLogFlag)")
    public void pointCut() {
    }

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取操作人信息
        // 通过解析请求头中的token来获取id信息
        String token = httpServletRequest.getHeader("token");
//        log.info("token: {}", token);
        String s = JWTUtils.parseJWT(token);
//        log.info("parsedToken: {}", s);
        Pattern pattern = Pattern.compile("id=(\\d+)(.0)?,?");
        Matcher matcher = pattern.matcher(s);
        int operateUser = 0;
        if (matcher.find()) { // 要使用find才会查找，直接使用group会报错
            operateUser = Integer.parseInt(matcher.group(1));
        }

        // 操作时间
        LocalDateTime operateTime = LocalDateTime.now();

        // 操作方法相关信息
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        String methodParams = Arrays.toString(joinPoint.getArgs());

        // 方法耗时
        Long startTime = System.currentTimeMillis();

        Object result = joinPoint.proceed();

        Long endTime = System.currentTimeMillis();
        Long costTime = endTime - startTime;

        String returnValue = result.toString();

        // 记录进日志实体类
        OperateLog operateLog = new OperateLog(null, operateUser, operateTime, className, methodName, methodParams, returnValue, costTime);
        operateLogMapper.insertOperateLog(operateLog);

        log.info("插入操作日志: {}", operateLog);

        return result;
    }
}
