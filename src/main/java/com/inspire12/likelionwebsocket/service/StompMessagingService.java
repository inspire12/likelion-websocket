package com.inspire12.likelionwebsocket.service;

import com.inspire12.likelionwebsocket.model.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StompMessagingService {

    private final SimpMessagingTemplate messagingTemplate;

    public void sendToTopic(ChatMessage message) {
        messagingTemplate.convertAndSend("/topic/public", message);
    }

    public void sendToUser(String user, ChatMessage message) {
        messagingTemplate.convertAndSendToUser(user, "/queue/private", message);
    }
}
