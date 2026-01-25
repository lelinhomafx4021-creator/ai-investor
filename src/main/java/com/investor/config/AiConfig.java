package com.investor.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import lombok.RequiredArgsConstructor;
@Configuration
@RequiredArgsConstructor
public class AiConfig {


    @Bean
    public ChatClient chatClient(ChatMemory chatMemory, StockTools stockTools, ChatClient.Builder builder) {
        return builder.defaultSystem("""
                你是一个专业的股票投资顾问。
                你可以帮助用户查询股票信息、分析市场走势、提供投资建议。
                请用专业但易懂的语言回答用户问题。
                """)
                .defaultTools(stockTools)
                .build();

    }

}