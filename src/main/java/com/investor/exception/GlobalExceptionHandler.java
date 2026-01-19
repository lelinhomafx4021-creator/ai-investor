package com.investor.exception;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.investor.common.Result;

@RestControllerAdvice
public class GlobalExceptionHandler {

     @ExceptionHandler(MethodArgumentNotValidException.class)
     public Result<Void> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
         return Result.fail(e.getBindingResult().getFieldError().getDefaultMessage());
     }
      @ExceptionHandler(Exception.class)
      public Result<Void> handleException(Exception e) {
          return Result.fail(e.getMessage());
      }
    
}
