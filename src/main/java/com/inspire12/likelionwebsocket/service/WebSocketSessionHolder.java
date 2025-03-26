package com.inspire12.likelionwebsocket.service;

import lombok.Getter;
import org.springframework.web.socket.WebSocketSession;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class WebSocketSessionHolder {
    // 연결된 모든 세션을 저장할 스레드 안전한 Set
    @Getter
    private static final Set<WebSocketSession> sessions = new CopyOnWriteArraySet<>();

    public static void add(WebSocketSession session) {
        sessions.add(session);
    }

    public static void remove(WebSocketSession session) {
        sessions.remove(session);
    }
}
