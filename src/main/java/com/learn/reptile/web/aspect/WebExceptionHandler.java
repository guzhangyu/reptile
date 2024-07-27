package com.learn.reptile.web.aspect;

import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.learn.reptile.entity.vo.R;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author zhangyugu
 * @Date 2021/4/24 7:30 下午
 * @Version 1.0
 */
@Slf4j
@ControllerAdvice
public class WebExceptionHandler {


    @ResponseBody
    @ExceptionHandler
    public R exceptionHandler(Exception e) { 
        log.error(e.getMessage(), e);
        if(e instanceof UnauthorizedException) {
            return R.failWithCode(401, e.getMessage());
        }
        if(e instanceof AccountException) {
            return R.failWithCode(7777, e.getMessage());
        }
        return R.fail(e.getMessage());
    }
}
