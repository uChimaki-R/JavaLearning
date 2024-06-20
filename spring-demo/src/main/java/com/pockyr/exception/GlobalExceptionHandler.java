package com.pockyr.exception;

import com.pockyr.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class) // 捕获全部异常
    public Result exception(Exception e) {
//        e.printStackTrace();
        log.error(e.getCause().toString());
        return Result.error("抱歉，操作失败，请联系管理员");
    }
}
