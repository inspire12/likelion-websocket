package com.inspire12.likelionwebsocket.holder;

import org.springframework.web.socket.WebSocketSession;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class WebSocketSessionHolder {
    public static Set<WebSocketSession> sessions = new CopyOnWriteArraySet<>();

    public static void addSession(WebSocketSession session) {
        sessions.add(session);
    }

    public static Set<WebSocketSession> getSessions() {
        return sessions;
    }
}
