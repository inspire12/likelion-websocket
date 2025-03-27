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

    private final ObjectMapper mapper;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new ChatWebSocketHandler(mapper),  "/ws")
//                .setAllowedOrigins("http://localhost:3000");
                .setAllowedOrigins("*");
    }
}
