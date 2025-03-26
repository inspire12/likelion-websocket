package com.inspire12.likelionwebsocket.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inspire12.likelionwebsocket.model.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final ObjectMapper objectMapper;

    public ChatMessage createWelcomeMessage(ChatMessage chatMessage) {
        return ChatMessage.createWelcomeMessage(chatMessage.getSender());
    }

    public void broadcastMessage(ChatMessage message) throws Exception {

        String messageJson = objectMapper.writeValueAsString(message);

        for (WebSocketSession webSocketSession : WebSocketSessionHolder.getSessions()) {
            if (webSocketSession.isOpen()) {
                webSocketSession.sendMessage(new TextMessage(messageJson));
            }
        }
    }
}
