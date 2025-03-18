package com.inspire12.likelionwebsocket.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver;


public class CustomUserResolver implements HandlerMethodArgumentResolver {

    private Logger log = LoggerFactory.getLogger(this.getClass().getName());
    @Override
    public boolean supportsParameter(MethodParameter parameter) {

        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, Message<?> message) throws Exception {
        return null;
    }
}
