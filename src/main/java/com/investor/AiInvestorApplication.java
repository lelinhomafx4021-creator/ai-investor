package com.investor;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * AI 量化投资平台启动类
 */
@MapperScan("com.investor.mapper")
@SpringBootApplication
@EnableScheduling
public class AiInvestorApplication {
    public static void main(String[] args) {
        SpringApplication.run(AiInvestorApplication.class, args);
    }
}
 