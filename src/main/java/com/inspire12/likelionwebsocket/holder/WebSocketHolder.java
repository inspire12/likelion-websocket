package com.inspire12.likelionwebsocket.holder;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;


public class WebSocketHolder {
    @Getter
    public static final Set<WebSocketSession> sessions = new CopyOnWriteArraySet<>();

    public static void addSession(WebSocketSession session) {
        sessions.add(session);
    }
}
