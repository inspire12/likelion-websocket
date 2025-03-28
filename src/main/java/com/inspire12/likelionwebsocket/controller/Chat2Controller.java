package com.inspire12.likelionwebsocket.controller;

import com.inspire12.likelionwebsocket.model.ChatMessage;
import com.inspire12.likelionwebsocket.service.StompMessagingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class Chat2Controller {

    private final StompMessagingService stompMessagingService;

    @PostMapping("/call")
    public ChatMessage call(
            @RequestBody ChatMessage chatMessage
    ) {
        stompMessagingService.sendToTopic(chatMessage);
        return chatMessage;
    }

    @PostMapping("/call/user")
    public ChatMessage callPrivate(
            @RequestBody ChatMessage chatMessage,
            @RequestParam String username
    ) {
        stompMessagingService.sendToUser(username, chatMessage);

        return chatMessage;
    }
}
