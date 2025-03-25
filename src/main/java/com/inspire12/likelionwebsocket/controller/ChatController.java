package com.inspire12.likelionwebsocket.controller;

import com.inspire12.likelionwebsocket.config.WebSocketConfig;
import com.inspire12.likelionwebsocket.model.ChatMessage;
import com.inspire12.likelionwebsocket.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    MessageService messageService;

    @PostMapping("/system")
    public void createMessage(@RequestBody ChatMessage chatMessage) {
        messageService.createSystemMessage(chatMessage);
    }
}
