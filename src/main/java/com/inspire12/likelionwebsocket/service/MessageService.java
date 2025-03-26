package com.inspire12.likelionwebsocket.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inspire12.likelionwebsocket.model.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final ChatWebSocketHandler chatWebSocketHandler;
    private final ObjectMapper objectMapper;

    public ChatMessage createWelcomeMessage(ChatMessage chatMessage) {
        return ChatMessage.createWelcomeMessage(chatMessage.getSender());
    }

    public ChatMessage sendAllMessage(ChatMessage chatMessage) {
        try {
            TextMessage textMessage = new TextMessage(objectMapper.writeValueAsBytes(chatMessage));
            Set<WebSocketSession> sessions = chatWebSocketHandler.getSessions();
            for (WebSocketSession session : sessions) {
                if (session.isOpen()) {
                    session.sendMessage(textMessage);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return chatMessage;
    }
}
