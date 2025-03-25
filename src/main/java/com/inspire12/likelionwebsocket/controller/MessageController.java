package com.inspire12.likelionwebsocket.controller;

import com.inspire12.likelionwebsocket.model.ChatMessage;
import com.inspire12.likelionwebsocket.model.ServerMessage;
import com.inspire12.likelionwebsocket.service.ChatWebSocketHandler;
import com.inspire12.likelionwebsocket.service.MessageService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MessageController {
    private final ChatWebSocketHandler handler;
    private final MessageService messageService;

    @PostMapping("/call")
    public ChatMessage call(@RequestBody ServerMessage serverMessage) throws Exception {
        return messageService.sendServerMessage(serverMessage);
    }
}
