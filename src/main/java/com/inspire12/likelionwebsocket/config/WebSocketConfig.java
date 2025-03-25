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

    //spring : spring ios라는 저장소에 저장하고 꺼내쓴다
    //chatWebSocketHandler를 컨트롤러 등 다른 파일에서도 사용할 수 있게 하기 위해 빼둠
    public ChatWebSocketHandler chatWebSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        chatWebSocketHandler = new ChatWebSocketHandler(mapper);
        registry.addHandler(chatWebSocketHandler, "/ws")
//                .setAllowedOrigins("http://localhost:3000");
                .setAllowedOrigins("*");
    }
}
