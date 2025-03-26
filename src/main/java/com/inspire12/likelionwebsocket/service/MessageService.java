package com.inspire12.likelionwebsocket.service;

import com.inspire12.likelionwebsocket.model.ChatMessage;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class MessageService {

    private final ChatWebSocketHandler chatWebSocketHandler;

    public MessageService(ChatWebSocketHandler chatWebSocketHandler) {
        this.chatWebSocketHandler = chatWebSocketHandler;
    }

    public ChatMessage createWelcomeMessage(ChatMessage chatMessage) {
        return ChatMessage.createWelcomeMessage(chatMessage.getSender());
    }

    public ChatMessage sendMessage(ChatMessage chatMessage) {
        return chatWebSocketHandler.sendMessageToAll(chatMessage);
    }
}
