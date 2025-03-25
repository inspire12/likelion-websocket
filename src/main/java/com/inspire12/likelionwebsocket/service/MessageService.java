package com.inspire12.likelionwebsocket.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inspire12.likelionwebsocket.model.ChatMessage;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Set;

@Service
public class MessageService {
    private final ChatWebSocketHandler chatWebSocketHandler;
    private final ObjectMapper objectMapper;

    public MessageService(ChatWebSocketHandler chatWebSocketHandler, ObjectMapper objectMapper) {
        this.chatWebSocketHandler = chatWebSocketHandler;
        this.objectMapper = objectMapper;
    }

    public ChatMessage sendMessage(ChatMessage chatMessage) {

        try {
            TextMessage messageToSend = new TextMessage(objectMapper.writeValueAsBytes(chatMessage));
            Set<WebSocketSession> sessions = chatWebSocketHandler.getSessions(); // 핸들러에서 세션 가져오기

            // 모든 세션에 메시지 전송
            for (WebSocketSession session : sessions) {
                if (session.isOpen()) {
                    session.sendMessage(messageToSend);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return chatMessage;
    }

    public ChatMessage createWelcomeMessage(ChatMessage chatMessage) {
        return ChatMessage.createWelcomeMessage(chatMessage.getSender());
    }
}
