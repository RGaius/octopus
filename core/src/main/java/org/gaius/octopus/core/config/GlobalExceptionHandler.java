package org.gaius.octopus.core.config;

import lombok.extern.slf4j.Slf4j;
import org.gaius.octopus.common.exception.ResponseCode;
import org.gaius.octopus.common.model.Result;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author zhaobo
 * @program octopus
 * @description 全局异常处理
 * @date 2024/6/29
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.OK)
    public Result handleResetControllerException(Exception ex) {
        log.error("系统异常", ex);
        // 根据需要返回适当的HTTP状态码和错误信息
        return Result.failure(ResponseCode.SYSTEM_ERROR, "系统繁忙");
    }
}
