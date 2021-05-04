package com.mao.whiteserver.exception;

import com.mao.whiteserver.result.Result;
import com.mao.whiteserver.result.ResultFactory;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;



@ControllerAdvice
public class DefaultExceptionHandler {
    @ExceptionHandler(UnauthorizedException.class)
    @ResponseBody
    public Result handleAuthorizationException(UnauthorizedException e) {
        String message = "权限认证失败";
        Logger logger = LoggerFactory.getLogger(this.getClass());
        logger.debug(message);
        return ResultFactory.buildFailResult(message);
    }
}
