package com.inspire12.likelionwebsocket.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inspire12.likelionwebsocket.model.ChatMessage;
import com.inspire12.likelionwebsocket.service.ChatWebSocketHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.TextMessage;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageApiController {

    private final ChatWebSocketHandler chatWebSocketHandler;
    private final ObjectMapper objectMapper;

    @PostMapping("/broadcast")
    public ResponseEntity<String> broadcastMessage(@RequestBody ChatMessage message) {
        try {
            // 메시지를 TextMessage로 변환
            TextMessage textMessage = new TextMessage(objectMapper.writeValueAsString(message));

            // 핸들러의 세션들에게 직접 메시지 전송
            chatWebSocketHandler.getSessions().forEach(session -> {
                if (session.isOpen()) {
                    try {
                        session.sendMessage(textMessage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            return ResponseEntity.ok("메시지가 성공적으로 전송되었습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("메시지 전송 중 오류가 발생했습니다.");
        }
    }
}