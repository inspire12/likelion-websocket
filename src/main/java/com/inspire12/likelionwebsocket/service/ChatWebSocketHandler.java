package com.inspire12.likelionwebsocket.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inspire12.likelionwebsocket.model.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.PongMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.w3c.dom.Text;

import java.security.Principal;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {
    // 연결된 모든 세션을 저장할 스레드 안전한 Set
    private final Set<WebSocketSession> sessions = new CopyOnWriteArraySet<>();
    private final ObjectMapper objectMapper;

    public ChatWebSocketHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 수신한 메시지를 모든 세션에 브로드캐스트
        ChatMessage chatMessage = objectMapper.readValue(message.getPayload(), ChatMessage.class);
        if (chatMessage.getType() == ChatMessage.MessageType.JOIN) {
            chatMessage = ChatMessage.createWelcomeMessage(chatMessage.getSender());
        }

        sendBoradcastMessage(chatMessage);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
    }

    public void sendBoradcastMessage(ChatMessage chatMessage) throws Exception {
        TextMessage messageToSend = toTextMessage(chatMessage);
        for (WebSocketSession webSocketSession : sessions) {
            if (webSocketSession.isOpen()) {
                webSocketSession.sendMessage(messageToSend);
            }
        }
    }

    private TextMessage toTextMessage(Object messageObject) throws JsonProcessingException {
        return new TextMessage(objectMapper.writeValueAsBytes(messageObject));
    }
}

