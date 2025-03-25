package com.inspire12.likelionwebsocket.service;

import com.inspire12.likelionwebsocket.model.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageService {

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
