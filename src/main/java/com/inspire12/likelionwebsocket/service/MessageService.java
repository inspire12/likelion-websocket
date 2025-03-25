package com.inspire12.likelionwebsocket.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inspire12.likelionwebsocket.model.ChatMessage;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.Set;

@Service
public class MessageService {

    private final ChatWebSocketHandler chatWebSocketHandler;
    private final ObjectMapper objectMapper;

    public MessageService(ObjectMapper objectMapper, ChatWebSocketHandler chatWebSocketHandler) {
        this.chatWebSocketHandler = chatWebSocketHandler;
        this.objectMapper = objectMapper;
    }

    public ChatMessage createWelcomeMessage(ChatMessage chatMessage) {
        return ChatMessage.createWelcomeMessage(chatMessage.getSender());
    }

//    public ChatMessage broadcastReply() {
//        return ChatMessage.broadcastReply();
//    }

    public ChatMessage broadcastReply(ChatMessage chatMessage) {
        try {
            TextMessage messageToSend = new TextMessage(objectMapper.writeValueAsBytes(chatMessage));
            Set<WebSocketSession> sessions = chatWebSocketHandler.getSessions();
            for (WebSocketSession webSocketSession : sessions) {
                System.out.println(webSocketSession.isOpen());
                webSocketSession.sendMessage(messageToSend);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return chatMessage;

    }
}
