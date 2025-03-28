
package com.inspire12.likelionwebsocket.listener;

import com.inspire12.likelionwebsocket.model.ChatMessage;
import com.inspire12.likelionwebsocket.service.StompMessagingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.security.Principal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WebSocketSessionListenManager {

    private final Map<String, Principal> sessions = new ConcurrentHashMap<>();
    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());
    private final StompMessagingService stompMessagingService;

    public WebSocketSessionListenManager(StompMessagingService stompMessagingService) {
        this.stompMessagingService = stompMessagingService;
    }

    @EventListener
    public void handleSessionConnected(SessionConnectedEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        Principal user = headerAccessor.getUser();
        log.info("user session connect: {} ", user);
        if (user != null) {
            sessions.put(user.getName(), user);
            stompMessagingService.sendToTopic(ChatMessage.createWelcomeMessage(user.getName()));
        }
        System.out.println("current users: " + sessions.size());
    }

    @EventListener
    public void handleSessionDisconnected(SessionDisconnectEvent event) {
        StompHeaderAccessor sha = StompHeaderAccessor.wrap(event.getMessage());
        Principal user = sha.getUser();
        if (user != null) {
            sessions.remove(user.getName());
        }
        System.out.println("current users: " + sessions.size());
    }

    // activeUser
    public Set<String> getConnectedUsers() {
        return sessions.keySet();
    }
}
