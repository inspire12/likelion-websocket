package com.inspire12.likelionwebsocket.service;

import com.inspire12.likelionwebsocket.model.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MessageService {

    public ChatMessage createWelcomeMessage(ChatMessage chatMessage) {
        return ChatMessage.createWelcomeMessage(chatMessage);
    }

    //시스템 메시지 메소드
    public void createSystemMessage(ChatMessage chatMessage) {
        ChatMessage.createMessage(chatMessage);
        ChatMessage.createSystemMessage(chatMessage);
    }
}
