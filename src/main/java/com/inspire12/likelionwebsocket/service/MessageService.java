package com.inspire12.likelionwebsocket.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inspire12.likelionwebsocket.model.ChatMessage;
import java.io.IOException;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final ChatWebSocketHandler chatWebSocketHandler;
    private final ObjectMapper objectMapper;

    public ChatMessage createWelcomeMessage(ChatMessage chatMessage) {
        return ChatMessage.createWelcomeMessage(chatMessage.getSender());
    }

    /**
     * 클라이언트로부터 전달받은 ChatMessage 를 모든 WebSocket 세션에 브로드캐스트 합니다.
     * @param chatMessage 전달받은 chatMessage
     */
    public void sendMessage(ChatMessage chatMessage) {
        try {
            // ChatMessage JSON 형태로 직렬화하여 TextMessage로 변환
            TextMessage messageToSend = new TextMessage(
                    objectMapper.writeValueAsBytes(chatMessage));
            // 연결된 모든 WebSocket 세션 가져옴
            Set<WebSocketSession> sessions = chatWebSocketHandler.getSessions();
            // 모든 세션에 메시지 전송
            for (WebSocketSession session : sessions) {
                session.sendMessage(messageToSend);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
