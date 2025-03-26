package com.inspire12.likelionwebsocket.controller;

import com.inspire12.likelionwebsocket.model.ChatMessage;
import com.inspire12.likelionwebsocket.service.MessageService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatAdminController {

    private final MessageService messageService;

    public ChatAdminController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/call")
    public ChatMessage call(@RequestBody ChatMessage chatMessage) {
        return messageService.sendMessage(chatMessage);
    }
}
