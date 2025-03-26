package com.inspire12.likelionwebsocket.holder;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;


@Slf4j
public class WebSocketSessionHolder {
    public static Set<WebSocketSession> sessions = new CopyOnWriteArraySet<>();

    public static void addSession(WebSocketSession session) {
        if (sessions.size() ==  999) {
            log.info("sessions size is {}", sessions.size());
        }
        sessions.add(session);
    }

    public static Set<WebSocketSession> getSessions() {
        return sessions;
    }

}
