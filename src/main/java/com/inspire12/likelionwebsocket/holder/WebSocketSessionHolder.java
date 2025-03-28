package com.inspire12.likelionwebsocket.holder;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

@Component
public class WebSocketSessionHolder {
  public static Set<WebSocketSession> sessions = new CopyOnWriteArraySet<>();

}