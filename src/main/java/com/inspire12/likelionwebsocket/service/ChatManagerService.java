package com.inspire12.likelionwebsocket.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inspire12.likelionwebsocket.model.ChatMessage;
import com.inspire12.likelionwebsocket.model.ChatSessionManagerMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class ChatManagerService {

    private final ChatWebSocketHandler chatWebSocketHandler;
    private final ObjectMapper objectMapper;


    public ChatSessionManagerMessage getMessage() {
        return new ChatSessionManagerMessage(chatWebSocketHandler.getSessions().size());
    }

    public ChatSessionManagerMessage sendAllMessage(ChatMessage chatMessage) throws IOException {
        Set<WebSocketSession> sessions = chatWebSocketHandler.getSessions();
        TextMessage messageToSend = new TextMessage(objectMapper.writeValueAsBytes(chatMessage));

        for (WebSocketSession webSocketSession : sessions) {
            if (webSocketSession.isOpen()) {
                webSocketSession.sendMessage(messageToSend);
            }
        }
        return new ChatSessionManagerMessage(chatWebSocketHandler.getSessions().size());
    }

}
