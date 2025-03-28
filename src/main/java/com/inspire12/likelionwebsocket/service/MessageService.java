package com.inspire12.likelionwebsocket.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inspire12.likelionwebsocket.model.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final ChatWebSocketHandler handler;
    private final ObjectMapper mapper;

    public ChatMessage createWelcomeMessage(ChatMessage chatMessage) {
        return ChatMessage.createWelcomeMessage(chatMessage.getSender());
    }

    public ChatMessage sendMessageToAll(ChatMessage chatMessage) {
        try {
            Set<WebSocketSession> sessions = handler.getSessions();
            TextMessage messageToSend = new TextMessage(mapper.writeValueAsBytes(chatMessage));
            for (WebSocketSession session : sessions) {
                if (session.isOpen()) session.sendMessage(messageToSend);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return chatMessage;
    }
}
