package com.inspire12.likelionwebsocket.controller;

import com.inspire12.likelionwebsocket.model.ChatMessage;
import com.inspire12.likelionwebsocket.service.MessageService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReplyMessageController {

    private final MessageService messageService;

    public ReplyMessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/reply")
    public ChatMessage ReplyMessage(@RequestBody ChatMessage chatMessage) {
        return messageService.broadcastReply(chatMessage);
    }

}
