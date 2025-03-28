package com.inspire12.likelionwebsocket.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inspire12.likelionwebsocket.model.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
@RequiredArgsConstructor
public class ChatWebSocketHandler extends TextWebSocketHandler {
    private final Set<WebSocketSession> sessions = new CopyOnWriteArraySet<>();
    private final ObjectMapper objectMapper;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        sendBroadcast(message);
    }

    private void sendBroadcast(TextMessage message) throws Exception{
        for (WebSocketSession webSocketSession : sessions) {
            if(webSocketSession.isOpen()){
                webSocketSession.sendMessage(toSendMessage(message));
            }
        }
    }
    private TextMessage toSendMessage(TextMessage message) throws JsonProcessingException {
        ChatMessage chatMessage = objectMapper.readValue(message.getPayload(), ChatMessage.class);
        if (chatMessage.getType() == ChatMessage.MessageType.JOIN) {
            return getWelcomeMessage(chatMessage);
        }
        return message;
    }

    private TextMessage getWelcomeMessage(ChatMessage chatMessage) throws JsonProcessingException {
        ChatMessage welcomeMessage = ChatMessage.createWelcomeMessage(chatMessage.getSender());
        return new TextMessage(objectMapper.writeValueAsBytes(welcomeMessage));
    }
}
