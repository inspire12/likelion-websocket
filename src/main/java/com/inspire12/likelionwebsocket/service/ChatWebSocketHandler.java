package com.inspire12.likelionwebsocket.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.inspire12.likelionwebsocket.model.ChatMessage;
import com.inspire12.likelionwebsocket.model.ChatMessage.MessageType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.PongMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.security.Principal;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class ChatWebSocketHandler extends TextWebSocketHandler {
    // 연결된 모든 세션을 저장할 스레드 안전한 Set
    private final Set<WebSocketSession> sessions = new CopyOnWriteArraySet<>();
    private final ObjectMapper mapper;

    public ChatWebSocketHandler(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    // 연결되었을때 session 따로 저장
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
    }

    // 메시지가 왔을 때 연결된 웹소켓 세션들에 있는 메시지를 보내는 작업
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message)
            throws Exception {
        ChatMessage chatMessage = mapper.readValue(message.getPayload(), ChatMessage.class);
        TextMessage messageToSend = message;
        if (chatMessage.getType() == MessageType.JOIN){
            ChatMessage joinMessage = ChatMessage.createWelcomeMessage(chatMessage.getSender());
            messageToSend = new TextMessage(mapper.writeValueAsString(joinMessage));
        }

        for (WebSocketSession toSession : sessions) {
            if (toSession.isOpen()){
                toSession.sendMessage(messageToSend);
            }
        }

    }

    // 연결이 끊겼을 때 session을 삭제
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status)
            throws Exception {
        sessions.remove(session);
    }
}

