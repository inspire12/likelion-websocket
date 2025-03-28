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

public class ChatWebSocketHandler extends TextWebSocketHandler{
    // 연결된 모든 세션을 저장할 스레드 안전한 Set
    // session 관리 방법을 따로 제공 안하므로 세션 저장소를 내부에 구현해둔다
    private final Set<WebSocketSession> sessions = new CopyOnWriteArraySet<>();
    private final ObjectMapper objectMapper;

    public ChatWebSocketHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    // 연결확립 -> 세션 저장소에 저장
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        ChatMessage chatMessage = objectMapper.readValue(message.getPayload(), ChatMessage.class);
        TextMessage messageToSend = message;

        // 메시지 타입이 신규 join인 경우 messageToSend를 welcomeMessage로 덮어씀
        if (chatMessage.getType() == ChatMessage.MessageType.JOIN) {
            ChatMessage welcomeMessage = ChatMessage.createWelcomeMessage(chatMessage.getSender());
            messageToSend = new TextMessage(objectMapper.writeValueAsBytes(welcomeMessage));
        }

        // sessions 내에 반복하면서 메시지 전송 -> broadcast 구현
        for (WebSocketSession webSocketSession : sessions) {
            if (webSocketSession.isOpen()) {
                webSocketSession.sendMessage(message);
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
    }
}

