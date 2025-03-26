package com.inspire12.likelionwebsocket.service;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inspire12.likelionwebsocket.holder.WebSocketSessionHolder;
import com.inspire12.likelionwebsocket.model.ChatMessage;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageService {

    // private final ChatWebSocketHandler chatWebSocketHandler;
    private final ObjectMapper objectMapper;

    public ChatMessage sendToAll(ChatMessage chatMessage) throws RuntimeException {
        try {
            TextMessage messageToSend = new TextMessage(objectMapper.writeValueAsBytes(chatMessage));
            // Bean에 등록된 ChatWebSocketHandler의 sessions에 있는 모든 세션에 메시지를 보내기 위해서
            // Set으로 설정된 세션을 전부 가져옴
            Set<WebSocketSession> sessions = WebSocketSessionHolder.getSessions();
            for (WebSocketSession session : sessions) {
                session.sendMessage(messageToSend);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return chatMessage;
    }
}
