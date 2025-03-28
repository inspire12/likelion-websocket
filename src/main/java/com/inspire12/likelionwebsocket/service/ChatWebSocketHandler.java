package com.inspire12.likelionwebsocket.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.inspire12.likelionwebsocket.model.ChatMessage;
import com.inspire12.likelionwebsocket.model.ChatMessage.MessageType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.PongMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.security.Principal;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class ChatWebSocketHandler extends TextWebSocketHandler {
    // 연결된 모든 세션을 저장할 스레드 안전한 Set
    private final Set<WebSocketSession> sessions = new CopyOnWriteArraySet<>();
    private final ObjectMapper mapper;

    public ChatWebSocketHandler(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        ChatMessage chatMessage = mapper.readValue(message.getPayload(), ChatMessage.class);
        TextMessage messageToSend = message;
        if (chatMessage.getType() == ChatMessage.MessageType.JOIN){
            ChatMessage joinMessage = ChatMessage.createWelcomeMessage(chatMessage.getSender());
            messageToSend = new TextMessage(mapper.writeValueAsBytes(joinMessage));
        }
        for (WebSocketSession webSocketSession: sessions){
            if (webSocketSession.isOpen()){
                webSocketSession.sendMessage(messageToSend);
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
    }
}

