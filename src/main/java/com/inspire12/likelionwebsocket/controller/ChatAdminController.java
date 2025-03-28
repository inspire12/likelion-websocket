package com.inspire12.likelionwebsocket.controller;

import com.inspire12.likelionwebsocket.model.ChatMessage;
import com.inspire12.likelionwebsocket.service.StompMessagingService;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatAdminController {

    private final StompMessagingService stompMessagingService;

    public ChatAdminController(StompMessagingService stompMessagingService) {
        this.stompMessagingService = stompMessagingService;
    }

    @PostMapping("/call")
    public ChatMessage call(@RequestBody ChatMessage chatMessage) {
        stompMessagingService.sendToTopic(chatMessage);
        return chatMessage;
    }

    @PostMapping("/call/user")
    public ChatMessage callUser(@RequestParam(name = "token") String username, @RequestBody ChatMessage chatMessage) {
        stompMessagingService.sendToUser(username, chatMessage);
        return chatMessage;
    }
}
