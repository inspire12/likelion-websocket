package com.inspire12.likelionwebsocket.service;

import com.inspire12.likelionwebsocket.model.ChatMessage;
import com.inspire12.likelionwebsocket.model.ServerMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final ChatWebSocketHandler chatWebSocketHandler;

    public ChatMessage sendServerMessage(ServerMessage serverMessage) throws Exception {
        ChatMessage messageToSend = ChatMessage.createServerMessage(serverMessage.getMessage());
        chatWebSocketHandler.sendBoradcastMessage(messageToSend);
        return messageToSend;
    }
    public ChatMessage createWelcomeMessage(ChatMessage chatMessage) {
        return ChatMessage.createWelcomeMessage(chatMessage.getSender());
    }
}
