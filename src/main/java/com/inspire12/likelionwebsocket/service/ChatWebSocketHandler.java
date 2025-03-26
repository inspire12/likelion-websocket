package com.inspire12.likelionwebsocket.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.inspire12.likelionwebsocket.model.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.PongMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.security.Principal;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@Service
public class ChatWebSocketHandler extends TextWebSocketHandler {
    // 연결된 모든 세션을 저장할 스레드 안전한 Set
    private final Set<WebSocketSession> sessions = new CopyOnWriteArraySet<>();
    private final ObjectMapper objectMapper;

    // 생성자로 di
    public ChatWebSocketHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
    }

    @Override// TODO 웹소켓 기본 라이브러리의 메서드를 오버라이드해 원하는 방식으로 메세지 핸들링
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 수신한 메시지를 모든 세션에 브로드캐스트
        ChatMessage chatMessage = objectMapper.readValue(message.getPayload(), ChatMessage.class);
        TextMessage messageToSend = message;
        if (chatMessage.getType() == ChatMessage.MessageType.JOIN) { // TODO 채팅방에 입장하면 연결이 시작되면 "~입장하였습니다" 메세지 자동으로 출력
            ChatMessage welcomeMessage = ChatMessage.createWelcomeMessage(chatMessage.getSender());
            messageToSend = new TextMessage(objectMapper.writeValueAsBytes(welcomeMessage));
        }

        System.out.println("[DEBUG] Current sessions size: " + sessions.size());
        for (WebSocketSession webSocketSession : sessions) { // 이미 모든 세션에 브로드 캐스팅하고 있음
            if (webSocketSession.isOpen()) {
                webSocketSession.sendMessage(messageToSend);
            }
        }
    }

    public void broadcast(ChatMessage chatMessage) throws Exception {
        System.out.println("[DEBUG] broadcast() called with: " + chatMessage);
        String payload = objectMapper.writeValueAsString(chatMessage);
        TextMessage messageToSend = new TextMessage(payload);

        System.out.println("[DEBUG] Current sessions size: " + sessions.size());
        for (WebSocketSession webSocketSession : sessions) {
            if (webSocketSession.isOpen()) {
                System.out.println("[DEBUG] Sending message to session " + webSocketSession.getId());
                webSocketSession.sendMessage(messageToSend);
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
    }
}

