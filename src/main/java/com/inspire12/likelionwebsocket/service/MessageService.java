package com.inspire12.likelionwebsocket.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inspire12.likelionwebsocket.model.ChatMessage;
import java.io.IOException;
import java.util.Set;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

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
            TextMessage messageToSend = new TextMessage(
                    objectMapper.writeValueAsBytes(chatMessage));
            Set<WebSocketSession> sessions = chatWebSocketHandler.getSessions();
            for (WebSocketSession session : sessions) {
                session.sendMessage(messageToSend);
            }
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
        return chatMessage;
    }

    public ChatMessage createChatMessage(ChatMessage chatMessage) {
        return ChatMessage.createWelcomeMessage(chatMessage.getSender());
    }

}
