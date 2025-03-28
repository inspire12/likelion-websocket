package com.inspire12.likelionwebsocket.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.inspire12.likelionwebsocket.model.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.security.Principal;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
@RequiredArgsConstructor
public class ChatWebSocketHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper;
    private final MessageService messageService;

    private final Set<WebSocketSession> sessions = new CopyOnWriteArraySet<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        ChatMessage chatMessage = objectMapper.readValue(message.getPayload(), ChatMessage.class);
        TextMessage sendMessage = message;
        if (chatMessage.getType() == ChatMessage.MessageType.JOIN) {
            sendMessage = new TextMessage(objectMapper.writeValueAsString(messageService.createWelcomeMessage(chatMessage)));
        }
        for (WebSocketSession webSocketSession : sessions) {
            webSocketSession.sendMessage(sendMessage);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        sessions.remove(session);
    }

}

