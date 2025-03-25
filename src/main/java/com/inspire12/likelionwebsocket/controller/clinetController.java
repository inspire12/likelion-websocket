package com.inspire12.likelionwebsocket.controller;

import com.inspire12.likelionwebsocket.model.ChatMessage;
import com.inspire12.likelionwebsocket.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class clinetController {
    private final MessageService messageService;

    @PostMapping("/call")
    public ChatMessage call(@RequestBody ChatMessage chatMessage) throws Exception {
        return messageService.sendMessage(chatMessage);
    }
}
