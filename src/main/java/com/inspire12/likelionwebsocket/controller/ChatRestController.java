package com.inspire12.likelionwebsocket.controller;

import com.inspire12.likelionwebsocket.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/message")
@RequiredArgsConstructor
public class ChatRestController {
    private final MessageService messageService;

    @PostMapping
    public void sendMessage(@RequestParam String sender, @RequestParam String message) throws Exception {
        messageService.sendMessage(sender, message);
    }
}
