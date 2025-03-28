package com.inspire12.likelionwebsocket.linstener;

import java.security.Principal;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

public class WebSocketListenManager {
  private final Map<String, String> sessions = new ConcurrentHashMap<>();

  @EventListener
  public void handleSessionConnected(SessionConnectedEvent event) {
    StompHeaderAccessor sha = StompHeaderAccessor.wrap(event.getMessage());
    Principal user = sha.getUser();
    if(user != null) {
      sessions.put(user.getName(), user.toString());
    }
  }

  @EventListener
  public void handleSessionDisconnected(SessionDisconnectEvent event) {
    StompHeaderAccessor sha = StompHeaderAccessor.wrap(event.getMessage());
    Principal user = sha.getUser();
    if(user != null) {
      sessions.remove(user.getName());
    }
  }

}
