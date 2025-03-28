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
@RequiredArgsConstructor
public class ChatWebSocketHandler extends TextWebSocketHandler {

    // 연결된 모든 세션을 저장할 스레드 안전한 Set
    private Set<WebSocketSession> sessions = new CopyOnWriteArraySet<>();

    // 메시지에 대한 형변환 및 처리
    private final ObjectMapper objectMapper;

    // 처음 연결
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message)
            throws Exception {
        ChatMessage chatMessage = objectMapper.readValue(message.getPayload().toString(),
                ChatMessage.class);
        if (MessageType.JOIN == chatMessage.getType()) {
            chatMessage = ChatMessage.createWelcomeMessage(chatMessage.getSender());
        }
        for (WebSocketSession socketSession : sessions) {
            if (socketSession.isOpen()) {
                socketSession.sendMessage(new TextMessage(objectMapper.writeValueAsBytes(chatMessage)));
            }
        }
    }

    // 연결 종료
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status)
            throws Exception {
        sessions.remove(session);
    }
}

