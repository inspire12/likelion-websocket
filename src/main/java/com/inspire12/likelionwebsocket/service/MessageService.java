package com.inspire12.likelionwebsocket.service;

import com.inspire12.likelionwebsocket.model.ChatMessage;

public class MessageService {

    public ChatMessage createWelcomeMessage(ChatMessage chatMessage) {
        return ChatMessage.createWelcomeMessage(chatMessage.getSender());
    }
}
