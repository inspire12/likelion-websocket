package com.inspire12.likelionwebsocket.controller;

import com.inspire12.likelionwebsocket.model.ChatMessage;
import com.inspire12.likelionwebsocket.service.ChatWebSocketHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatMessageRestController {

    private final ChatWebSocketHandler chatWebSocketHandler;

    @PostMapping("/broadcast")
    public void broadcast(@RequestBody ChatMessage chatMessage) throws Exception {
        chatWebSocketHandler.broadcast(chatMessage);
    }

}
