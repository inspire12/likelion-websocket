package com.inspire12.likelionwebsocket.service;

import com.inspire12.likelionwebsocket.model.ChatMessage;
import lombok.Builder;
import org.springframework.stereotype.Service;

@Service
@Builder
public class MessageService {

    private final ChatWebSocketHandler chatWebSocketHandler;

    public ChatMessage createWelcomeMessage(ChatMessage chatMessage) {
        return ChatMessage.createWelcomeMessage(chatMessage.getSender());
    }

    public boolean createSystemMessage(String message) {
        try {
            chatWebSocketHandler.broadcast(message);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
