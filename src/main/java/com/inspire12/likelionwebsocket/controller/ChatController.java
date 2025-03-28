package com.inspire12.likelionwebsocket.controller;

import com.inspire12.likelionwebsocket.model.ChatMessage;
import com.inspire12.likelionwebsocket.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

// incoming client requests to /app will be handled by controller
@RequiredArgsConstructor
@Controller
public class ChatController {

    private final MessageService messageService;

    /**
     * /app/chat.sendMessage 로 들어오는 메시지를 처리하여 /topic/public 로 전송
     * /app으로 들어오는 메시지를 어찌됐든 최종적으로 broker에게 전송해야 한다는 구조. 정확히는 client request format -> /app/chat.sendMessage
     */
    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(ChatMessage chatMessage) {
        return chatMessage;
    }

    /**
     * /app/chat.addUser 로 들어오는 메시지를 처리하여 /topic/public 로 전송
     * 동일하게, /app으로 들어오는 메시지를 어찌됐든 최종적으로 broker에게 전송해야 한다는 구조. 정확히는 client request format -> /app/chat.addUser
     */
    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(ChatMessage chatMessage) {
        return messageService.createWelcomeMessage(chatMessage);
    }
}
