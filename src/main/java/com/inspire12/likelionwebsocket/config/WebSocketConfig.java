package com.inspire12.likelionwebsocket.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.*;
import org.springframework.web.socket.server.HandshakeFailureException;
import org.springframework.web.socket.server.HandshakeHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
//                .setAllowedOrigins("http://localhost:3000")
                .setAllowedOriginPatterns("http://localhost:3000*")
                .setHandshakeHandler(new DefaultHandshakeHandler() {
                    @Override
                    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler handler, Map<String, Object> attributes) {
                        String query = request.getURI().getQuery();
                        if (query != null) {
                            return () -> query.split("token=")[1];
                        }
                        return null;
                    }
                })
                .withSockJS();
    }

    @Override
    public boolean configureMessageConverters(List<MessageConverter> messageConverters) {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE, true);
        converter.setObjectMapper(mapper);
        messageConverters.add(converter);

        return false;
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic", "/queue");
        registry.setApplicationDestinationPrefixes("/app");
        registry.setUserDestinationPrefix("/user");
    }
}

