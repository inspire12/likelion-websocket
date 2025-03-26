package com.inspire12.likelionwebsocket.controller;

import com.inspire12.likelionwebsocket.model.ChatMessage;
import com.inspire12.likelionwebsocket.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
@RequestMapping("/chat")
public class ChatController {

    private final MessageService messageService;

    @PostMapping("/all")
    public ChatMessage sendAllMessage(@RequestBody ChatMessage chatMessage) {
        return messageService.sendAllMessage(chatMessage);
    }
}
