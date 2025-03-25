package com.inspire12.likelionwebsocket.controller;

import com.inspire12.likelionwebsocket.model.ChatMessage;
import com.inspire12.likelionwebsocket.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SystemChatController {

    private final MessageService messageService;

    @PostMapping("/system/message")
    public void hello(@RequestBody ChatMessage chatMessage) {
        messageService.sendMessage(chatMessage);
    }
}
