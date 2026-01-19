package com.investor.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.investor.common.Result;

@RestControllerAdvice
public class GlobalExceptionHandler {
      @ExceptionHandler(Exception.class)
      public Result<Void> handleException(Exception e) {
          return Result.fail(e.getMessage());
      }
    
}
