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

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")     // lets client establish websocket connection on localhost:8083/ws - upon establishment, STOMP frame flow will start
                .setAllowedOrigins("http://localhost:3000")
                .withSockJS();
    }

    // message broker
    // compare with the image on spring docs, routing which messages will go through controller or directly to broker
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // STOMP messages with dest headers containing /app shall be routed to certain @MessageMapping methods
        registry.setApplicationDestinationPrefixes("/app");
        // or messages with dest headers containing /topic or /queue shall be routed directly to the broker
        registry.enableSimpleBroker("/topic");
    }

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
