package com.inspire12.likelionwebsocket.listener;

import com.inspire12.likelionwebsocket.config.WebSocketSessionMetrics;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@RequiredArgsConstructor
public class WebSocketEventListener {

    private final WebSocketSessionMetrics metrics;

    @EventListener
    public void handleSessionConnected(SessionConnectedEvent event) {
        metrics.sessionConnected();
    }

    @EventListener
    public void handleSessionDisconnect(SessionDisconnectEvent event) {
        metrics.sessionDisconnected();
    }
}
