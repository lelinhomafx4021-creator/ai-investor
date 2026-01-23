package com.investor.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class MarketWebSocketHandler extends TextWebSocketHandler {

    // 存储所有连接的客户端
    private final Set<WebSocketSession> sessions = ConcurrentHashMap.newKeySet();

    // 连接成功
    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session);
        log.info("客户端连接，当前在线: {}", sessions.size());
    }

    // 连接断开
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessions.remove(session);
        log.info("客户端断开，当前在线: {}", sessions.size());
    }

    // 广播给所有客户端
    public void broadcast(String message) {
        for (WebSocketSession session : sessions) {
            try {
                if (session.isOpen()) {
                    session.sendMessage(new TextMessage(message));
                }
            } catch (IOException e) {
                log.error("推送失败", e);
            }
        }
    }
}