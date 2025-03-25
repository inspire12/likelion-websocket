package com.inspire12.likelionwebsocket.controller;

import com.inspire12.likelionwebsocket.model.ChatMessage;
import com.inspire12.likelionwebsocket.service.ChatWebSocketHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/system")
@RequiredArgsConstructor
public class SystemChatController {

    private final ChatWebSocketHandler chatWebSocketHandler;

    @PostMapping("/call")
    public ResponseEntity<ChatMessage> sendMessage(@RequestBody ChatMessage message) {
        chatWebSocketHandler.sendSystemMessage(message);
        return ResponseEntity.ok(message);
    }
}
