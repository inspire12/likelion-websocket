package com.inspire12.likelionwebsocket.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.inspire12.likelionwebsocket.model.ChatMessage;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;


@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {
    private final ObjectMapper objectMapper;

    public ChatWebSocketHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        WebSocketSessionHolder.add(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 수신한 메시지를 모든 세션에 브로드캐스트
        ChatMessage chatMessage = objectMapper.readValue(message.getPayload(), ChatMessage.class);
        TextMessage messageToSend = message;
        if (chatMessage.getType() == ChatMessage.MessageType.JOIN) {
            ChatMessage welcomeMessage = ChatMessage.createWelcomeMessage(chatMessage.getSender());
            messageToSend = new TextMessage(objectMapper.writeValueAsBytes(welcomeMessage));
        }

        for (WebSocketSession webSocketSession : WebSocketSessionHolder.getSessions()) {
            if (webSocketSession.isOpen()) {
                webSocketSession.sendMessage(messageToSend);
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        WebSocketSessionHolder.remove(session);
    }
}

