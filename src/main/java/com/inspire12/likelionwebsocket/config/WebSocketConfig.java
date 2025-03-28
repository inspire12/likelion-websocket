package com.inspire12.likelionwebsocket.config;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.List;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer{

    //pub 경로 설정
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 메시지를 받을 때 경로
        registry.enableSimpleBroker("/topic");
        // 메시지를 보낼 때 경로
        registry.setApplicationDestinationPrefixes("/app");

    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // WebSocket 연결 엔드포인트 등록, SockJS fallback 제공
        registry.addEndpoint("/ws")
                .setAllowedOrigins("http://localhost:3000") // 클라이언트 주소 허용
                .withSockJS();
    }


    @Override
    public boolean configureMessageConverters(List<MessageConverter> messageConverters) {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        ObjectMapper mapper = new ObjectMapper();
        // unknown enum 값이 들어오면 mixin에서 지정한 default(StompCommand.SEND)를 사용하도록 설정
        mapper.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE, true);
        converter.setObjectMapper(mapper);
        messageConverters.add(converter);
        // false를 리턴하면 기본 변환기도 함께 등록되지만 여기서는 커스텀 변환기만 사용하도록 합니다.
        return false;
    }

}
