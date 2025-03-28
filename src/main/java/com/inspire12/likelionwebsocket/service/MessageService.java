package com.inspire12.likelionwebsocket.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inspire12.likelionwebsocket.holder.WebSocketSessionHolder;
import com.inspire12.likelionwebsocket.model.ChatMessage;
import java.io.IOException;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

@RequiredArgsConstructor
@Service
public class MessageService {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ObjectMapper objectMapper;

    public ChatMessage createWelcomeMessage(ChatMessage chatMessage) {
        return ChatMessage.createWelcomeMessage(chatMessage.getSender());
    }

    public ChatMessage sendMessage(ChatMessage chatMessage) {
        try {
            TextMessage messageToSend = new TextMessage(objectMapper.writeValueAsBytes(chatMessage));
            Set<WebSocketSession> sessions = WebSocketSessionHolder.sessions;
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

    public ChatMessage sendAdminMessage(ChatMessage chatMessage) {
        ChatMessage adminMessage = ChatMessage.createAdminMessage(chatMessage.getContent());
        simpMessagingTemplate.convertAndSend("/topic/public", adminMessage);
        return adminMessage;
    }
}
