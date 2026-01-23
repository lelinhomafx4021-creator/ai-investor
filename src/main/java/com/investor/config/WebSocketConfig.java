package com.investor.config;
import com.investor.websocket.MarketWebSocketHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
@Configuration
@EnableWebSocket  // 启用 WebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {
    private final MarketWebSocketHandler handler;  // 注入你的处理器
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // 注册端点：ws://localhost:8080/ws/market
        registry.addHandler(handler, "/ws/market")
                .setAllowedOrigins("*");  // 允许跨域
    }
}