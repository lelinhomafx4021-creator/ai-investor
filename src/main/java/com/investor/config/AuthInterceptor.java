package com.investor.config;

import com.investor.common.Result;
import com.investor.util.JwtUtil;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import cn.hutool.json.JSONUtil; 
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

@Slf4j
@Component
public class AuthInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handle) throws IOException {
        String token=request.getHeader("Authorization");
        if (token==null||token.isEmpty()){
            log.info("没有令牌，请先登录");
            sendError(response,"请先登录");
            response.setStatus(401);
            return false;
        }
        if(token.startsWith("Bearer ")){
            token=token.substring(7);
            boolean verify = JwtUtil.verify(token);
            if (!verify){
                log.info("token验证失败");
                sendError(response,"登录已过期，请重新登录");
                response.setStatus(401);
            }
            else {
                return true;
                
            }
        }
        return false;
    }
    public void sendError(HttpServletResponse response,String message) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(JSONUtil.toJsonStr(Result.fail(message)));
    }
    
}
