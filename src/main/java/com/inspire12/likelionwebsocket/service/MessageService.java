package com.inspire12.likelionwebsocket.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inspire12.likelionwebsocket.model.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.Set;

@Service
@RequiredArgsConstructor
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
            Set<WebSocketSession> sessions = chatWebSocketHandler.getSessions();
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

    private final ChatWebSocketHandler chatWebSocketHandler;

    @Deprecated
    public ChatMessage createWelcomeMessage(ChatMessage chatMessage) {
        return ChatMessage.createWelcomeMessage(chatMessage.getSender());
    }

    public void sendMessage(String sender, String message) throws Exception {
        chatWebSocketHandler.sendMessage(
                ChatMessage.builder()
                        .type(ChatMessage.MessageType.CHAT)
                        .sender(sender)
                        .content(message)
                        .build()
        );
    }
}
