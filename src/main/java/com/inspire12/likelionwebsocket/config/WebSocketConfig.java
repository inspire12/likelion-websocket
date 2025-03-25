package com.inspire12.likelionwebsocket.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inspire12.likelionwebsocket.service.ChatWebSocketHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.*;

@RequiredArgsConstructor
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    // 의존성을 자동으로 주입함으로 Config 클래스에서는 객체를 따로 생성할 필요가 없어졌음.
//    private final ObjectMapper mapper;
    private final ChatWebSocketHandler handler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // new로 ObjectMapper의 객체를 전달해 줄 필요가 없어졌음
        // registry.addHandler(new ChatWebSocketHandler(mapper),  "/ws")
        registry.addHandler(handler, "/ws")
//                .setAllowedOrigins("http://localhost:3000");
                .setAllowedOrigins("*");
    }
}
