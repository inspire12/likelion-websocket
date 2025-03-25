package com.inspire12.likelionwebsocket.service;


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

import java.security.Principal;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {
    // 연결된 모든 세션을 저장할 스레드 안전한 Set
    private final Set<WebSocketSession> sessions = new CopyOnWriteArraySet<>();
    private final ObjectMapper objectMapper;
    private final MessageService messageService = new MessageService();

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
        TextMessage messageToSend = message;
        TextMessage messageToSend2 = message;

        if (chatMessage.getType() == ChatMessage.MessageType.JOIN) {
            ChatMessage welcomeMessage = ChatMessage.createWelcomeMessage(chatMessage);
            messageToSend = new TextMessage(objectMapper.writeValueAsBytes(welcomeMessage));
        }

        if (chatMessage.getType() == ChatMessage.MessageType.CHAT) {
            ChatMessage welcomeMessage = ChatMessage.createMessage(chatMessage);
            messageToSend = new TextMessage(objectMapper.writeValueAsBytes(welcomeMessage));

            ChatMessage systemMessage = ChatMessage.createSystemMessage(chatMessage);
            messageToSend2 = new TextMessage(objectMapper.writeValueAsBytes(systemMessage));
        }

        for (WebSocketSession webSocketSession : sessions) {
            if (webSocketSession.isOpen()) {
                webSocketSession.sendMessage(messageToSend);
                webSocketSession.sendMessage(messageToSend2);
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
    }
}

