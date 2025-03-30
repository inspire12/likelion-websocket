package com.inspire12.likelionwebsocket.controller;

import com.inspire12.likelionwebsocket.model.ChatMessage;
import com.inspire12.likelionwebsocket.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/chat")
public class MessageController {
    @Autowired
    MessageService messageService;

    @PostMapping("/system")
    @ResponseBody
    public void createSystemMessage(@RequestBody ChatMessage chatMessage) {
        messageService.createSystemMessage(chatMessage);
    }
}
