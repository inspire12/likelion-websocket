package com.inspire12.likelionwebsocket.holder;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class WebSocketSessionHolder {

    public static Set<WebSocketSession> sessions = new CopyOnWriteArraySet<>();
}
