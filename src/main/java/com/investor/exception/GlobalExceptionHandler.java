package com.investor.exception;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.investor.common.Result;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 参数校验异常
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<Void> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return Result.failMsg(e.getBindingResult().getFieldError().getDefaultMessage());
    }

    // 数据库唯一键冲突异常（用户名重复等）
    @ExceptionHandler(org.springframework.dao.DuplicateKeyException.class)
    public Result<Void> handleDuplicateKeyException(org.springframework.dao.DuplicateKeyException e) {
        return Result.failMsg("用户已存在，请勿重复提交");
    }

    // 通用异常兜底
    @ExceptionHandler(Exception.class)
    public Result<Void> handleException(Exception e) {
        return Result.failMsg(e.getMessage());
    }

}
