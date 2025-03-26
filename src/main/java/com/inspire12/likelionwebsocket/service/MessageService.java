package com.inspire12.likelionwebsocket.service;

import com.inspire12.likelionwebsocket.model.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final ChatWebSocketHandler chatWebSocketHandler;

    public ChatMessage createWelcomeMessage(ChatMessage chatMessage) {
        return ChatMessage.createWelcomeMessage(chatMessage.getSender());
    }

    public void broadcastMessage(TextMessage message) throws Exception {
        chatWebSocketHandler.handleTextMessage(null, message);
    }
}
