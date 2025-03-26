package com.inspire12.likelionwebsocket.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inspire12.likelionwebsocket.holder.WebSocketSessionHolder;
import com.inspire12.likelionwebsocket.model.ChatMessage;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.Set;

@Service
public class MessageService {
    private final ObjectMapper objectMapper;

    public MessageService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public ChatMessage sendMessage(ChatMessage chatMessage) {

        try {
            TextMessage messageToSend = new TextMessage(objectMapper.writeValueAsBytes(chatMessage));
            Set<WebSocketSession> sessions = WebSocketSessionHolder.getSessions();
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
