package com.inspire12.likelionwebsocket.controller;

import com.inspire12.likelionwebsocket.model.ChatMessage;
import com.inspire12.likelionwebsocket.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class ChatController {

    private final MessageService messageService;

    //@MessageMapping는 클라이언트가 보낸 메시지를 특정 핸들러 메서드로 매핑
    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    // /app/chat.sendMessage 로 들어오는 메시지를 처리하여 /topic/public 로 전송
    public ChatMessage sendMessage(ChatMessage chatMessage) {
        return chatMessage;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    // /app/chat.addUser 로 들어오는 메시지를 처리하여 /topic/public 로 전송
    public ChatMessage addUser(ChatMessage chatMessage) {
        return messageService.createWelcomeMessage(chatMessage);
    }

}
