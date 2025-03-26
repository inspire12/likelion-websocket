package com.inspire12.likelionwebsocket.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.TextMessage;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AdminController {

    private final MessageService messageService;

    @PostMapping("/call")
    public ResponseEntity<Void> call(@RequestBody String message) {
        log.info("call message: {}", message);
        try {
            messageService.broadcastMessage(new TextMessage(message));
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
