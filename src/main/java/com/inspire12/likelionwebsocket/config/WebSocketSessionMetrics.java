package com.inspire12.likelionwebsocket.config;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class WebSocketSessionMetrics {

    private final AtomicInteger activeSessions = new AtomicInteger();

    public WebSocketSessionMetrics(MeterRegistry registry) {
        Gauge.builder("websocket.active.sessions", activeSessions, AtomicInteger::get)
             .description("현재 활성화된 WebSocket 연결 수")
             .register(registry);
    }

    public void sessionConnected() {
        activeSessions.incrementAndGet();
    }

    public void sessionDisconnected() {
        activeSessions.decrementAndGet();
    }
}