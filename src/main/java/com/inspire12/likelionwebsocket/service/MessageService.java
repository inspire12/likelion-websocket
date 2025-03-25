package com.inspire12.likelionwebsocket.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inspire12.likelionwebsocket.model.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final ChatWebSocketHandler chatWebSocketHandler;
    private final ObjectMapper objectMapper;

    public ChatMessage sendMessage(ChatMessage chatMessage) {

        try {
            TextMessage message = new TextMessage(objectMapper.writeValueAsBytes(chatMessage));
            for (WebSocketSession session : chatWebSocketHandler.getSessions()) {
                session.sendMessage(message);
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
