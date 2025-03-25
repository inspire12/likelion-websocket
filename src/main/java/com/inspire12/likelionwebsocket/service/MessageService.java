package com.inspire12.likelionwebsocket.service;

import com.inspire12.likelionwebsocket.model.ChatMessage;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    public ChatMessage createWelcomeMessage(ChatMessage chatMessage) {
        return ChatMessage.createWelcomeMessage(chatMessage.getSender());
    }

    public ChatMessage broadcastReply() {
        return ChatMessage.broadcastReply();
    }
}
