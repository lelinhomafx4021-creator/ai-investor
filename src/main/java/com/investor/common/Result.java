package com.investor.common;

import lombok.Data;

@Data
public class Result<T> {
    private int code;
    private String message;
    private T data;
    
    public static <T>Result<T> success( T data) {
        Result<T> result = new Result<>();
        result.code = 200;
        result.message = "success";
        result.data = data;
        return result;
    }
    public static <T>Result<T> success(String message) {
        Result<T> result = new Result<>();
        result.code = 200;
        return result;
    }
    
    public static <T>Result<T> fail(Integer code, String message) {
        Result<T> result = new Result<>();
        result.code = code;
        result.message = message;
        return result;
    }
    public static <T>Result<T> fail(String message) {
        Result<T> result = new Result<>();
        result.code = 500;
        result.message = message;
        return result;
    }


    
}
