package com.investor.common;

import lombok.Data;

@Data
public class Result<T> {
    private int code;
    private String message;
    private T data;

    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.code = 200;
        result.message = "success";
        result.data = data;
        return result;
    }
      public static <T> Result<T> successMsg(String message) {
        Result<T> result = new Result<>();
        result.code = 200;
        result.message = message;
        return result;
    }

    public static <T> Result<T> successMsg(String message,T data) {
        Result<T> result = new Result<>();
        result.code = 200;
        result.message = message; // 修复：添加 message 赋值
        result.data = data;
        return result;
    }

    public static <T> Result<T> failCMsg(Integer code, String message) {
        Result<T> result = new Result<>();
        result.code = code;
        result.message = message;
        return result;
    }

    public static <T> Result<T> failMsg(String message) {
        Result<T> result = new Result<>();
        result.code = 500;
        result.message = message;
        return result;
    }

}
