package com.inspire12.likelionwebsocket.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.inspire12.likelionwebsocket.model.ChatMessage;
import jakarta.websocket.Session;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.security.Principal;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {
    // 연결된 모든 세션을 저장할 스레드 안전한 Set
    @Getter
    private final Set<WebSocketSession> sessions = new CopyOnWriteArraySet<>();
    private final ObjectMapper objectMapper;


    public ChatWebSocketHandler(ObjectMapper objectMapper) {
       this.objectMapper = objectMapper;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
    }

    @Override
    //메시지가 왔을 때 연결된 웹소켓 세션들에게 메시지를 보내는 작업
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        //getPayload() : 전송한 메시지를 확인할 수 있음
        ChatMessage chatMessage = objectMapper.readValue(message.getPayload(), ChatMessage.class);
        TextMessage messageToSend = message;
        if(chatMessage.getType() == ChatMessage.MessageType.JOIN){
            ChatMessage welcomeMessage = ChatMessage.createWelcomeMessage(chatMessage.getSender());
            messageToSend = new TextMessage(objectMapper.writeValueAsBytes(welcomeMessage));
        }

        for(WebSocketSession toSession : sessions) {
            if(toSession.isOpen()){
                toSession.sendMessage(messageToSend);
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        sessions.remove(session);
    }
}

