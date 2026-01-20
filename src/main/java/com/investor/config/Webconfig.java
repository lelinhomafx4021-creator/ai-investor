package com.investor.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class Webconfig implements WebMvcConfigurer {
    private final AuthInterceptor authInterceptor;
    private final AdminInterceptor adminInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        // 登录注册
                        "/api/login",
                        "/api/register",
                        // Knife4j / Swagger 全部放行
                        "/doc.html",
                        "/doc.html/**",
                        "/webjars/**",
                        "/swagger-resources",
                        "/swagger-resources/**",
                        "/v3/api-docs",
                        "/v3/api-docs/**",
                        "/swagger-ui.html",
                        "/swagger-ui/**",
                        "/favicon.ico",
                        // 静态资源
                        "/static/**",
                        "/css/**",
                        "/js/**",
                        "/error");
    
    // 2. 管理员权限拦截器（在 AuthInterceptor 之后执行）
    registry.addInterceptor(adminInterceptor)
            .addPathPatterns("/api/admin/**");
    }
}
