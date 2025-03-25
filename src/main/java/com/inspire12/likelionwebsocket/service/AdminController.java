package com.inspire12.likelionwebsocket.service;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.TextMessage;

@RestController
public class AdminController {

    private final ChatWebSocketHandler chatWebSocketHandler;

    public AdminController(ChatWebSocketHandler chatWebSocketHandler) {
        this.chatWebSocketHandler = chatWebSocketHandler;
    }

    @PostMapping("/call")
    public String call(@RequestBody String message) throws Exception {
        chatWebSocketHandler.handleTextMessage(null, new TextMessage(message));

        return "ok";
    }
}
