package com.inspire12.likelionwebsocket.listener;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@RequiredArgsConstructor
public class WebSocketEventListener {
    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());

    @EventListener
    public void handleSessionConnect(SessionConnectEvent event) {
        log.info("connect {}", event);
    }

    @EventListener
    public void handleSessionConnected(SessionConnectedEvent event) {
        log.info("connected {}", event);
    }


    @EventListener
    public void handleSessionDisconnect(SessionDisconnectEvent event) {
        log.info("disconnect {}", event);
    }
}
