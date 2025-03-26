package com.inspire12.likelionwebsocket.controller;

import com.inspire12.likelionwebsocket.model.ChatMessage;
import com.inspire12.likelionwebsocket.service.ChatWebSocketHandler;
import com.inspire12.likelionwebsocket.service.MessageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;

import java.util.Set;

@RestController
public class ChatController {

    private final MessageService messageService;

    public ChatController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/call")
    public ChatMessage call(@RequestBody ChatMessage chatMessage) {
        return messageService.sendMessage(chatMessage);
    }
}
