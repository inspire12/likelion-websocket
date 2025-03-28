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
        /*
        프론트에서 받아온 메시지를 모든 유저에게 브로드 캐스팅 해주는 함수
        1.
         */
        // message.getPayload 함수를 통해 JSON 문자열을 받아옴 (프론트에서 백엔드와 약속한 형식의 JSON 문자열)
        // ChatMessage 클래스 형식으로 object mapper 를 통해 직렬화
        ChatMessage chatMessage = objectMapper.readValue(message.getPayload(), ChatMessage.class);

        TextMessage messageToSend = message;
        // 받아온 메시지 형식에 따라 비즈니스 로직 추가
        if(chatMessage.getType() == ChatMessage.MessageType.JOIN) {
            ChatMessage welcomeMessage = ChatMessage.createWelcomeMessage(chatMessage.getSender());
            messageToSend = new TextMessage((objectMapper.writeValueAsBytes(welcomeMessage)));
        }


        // 수신한 메시지를 모든 세션에 브로드캐스트
        for(WebSocketSession toSession: sessions) {
            if(toSession.isOpen()) {
                toSession.sendMessage(messageToSend);
            }

        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
    }


}

