package com.investor.config;

import com.investor.common.Result;
import com.investor.common.UserContents;
import com.investor.util.JwtUtil;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import cn.hutool.json.JSONUtil;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.util.Map;


@Slf4j
@Component
public class AdminInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws IOException {
        // 从 request 获取 role（AuthInterceptor 已经存好了）
        String role = UserContents.getUserRole();

        if (!"ADMIN".equals(role)) {
            log.info("非管理员访问管理接口");
            response.setStatus(403);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(JSONUtil.toJsonStr(Result.failMsg("无权限访问")));
            return false;
        }
        return true;
    }
}