package com.inspire12.likelionwebsocket.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inspire12.likelionwebsocket.service.ChatWebSocketHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.*;

@RequiredArgsConstructor
@Configuration
@EnableWebSocket // Spring WebSocket을 활성화하는 어노테이션
public class WebSocketConfig implements WebSocketConfigurer {

    private final ChatWebSocketHandler chatWebSocketHandler; // WebSocket 핸들러 등록

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(chatWebSocketHandler, "/ws") // "/ws" 경로로 들어오는 WebSocket 요청을 chatWebSockeHandler가 처리하도록 설정
                .setAllowedOrigins("*"); // 모든 도메인에서 WebSocket 접속을 허용
    }
}