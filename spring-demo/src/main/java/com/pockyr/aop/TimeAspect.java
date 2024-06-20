package com.pockyr.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Aspect // AOP类
public class TimeAspect {
    @Around("execution(* com.pockyr.service.*.*(..))") // 标记功能包装的方法范围(service中所有类和接口的所有方法) // 切入点表达式
    public Object recordTime(ProceedingJoinPoint joinPoint) throws Throwable {
        // 记录开始时间
        Long startTime = System.currentTimeMillis();

        // 调用原始方法, 需要将返回值返回回去
        Object result = joinPoint.proceed();

        // 记录结束时间, 计算方法执行耗时
        Long endTime = System.currentTimeMillis();

        log.info("[{}] taken: {} ms", joinPoint.getSignature(), endTime - startTime);
        return result;
    }
}
