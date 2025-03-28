package com.inspire12.likelionwebsocket.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inspire12.likelionwebsocket.model.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.security.Principal;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class ChatWebSocketHandler extends TextWebSocketHandler {
    private Set<WebSocketSession> sessions = new CopyOnWriteArraySet<>();
    private final ObjectMapper objectMapper;
    public ChatWebSocketHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        ChatMessage chatMessage = objectMapper.readValue(message.getPayload().toString(), ChatMessage.class);
        if (ChatMessage.MessageType.JOIN == chatMessage.getType()) {
            ChatMessage welComeMessage = ChatMessage
                    .builder()
                    .type(ChatMessage.MessageType.JOIN)
                    .content(chatMessage.getSender() + "님이 들어오셨습니다.")
                    .sender("System")
                    .build();
            for (WebSocketSession toSession : sessions) {
                if(toSession.isOpen()) {
                    toSession.sendMessage(new TextMessage(objectMapper.writeValueAsBytes(welComeMessage)));
                }
            }
        } else {
            for (WebSocketSession toSession : sessions) {
                if(toSession.isOpen()) {
                    toSession.sendMessage(message);
                }
            }
        }

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        sessions.remove(session);
    }
}
