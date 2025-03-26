package com.inspire12.likelionwebsocket.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inspire12.likelionwebsocket.model.ChatMessage;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {
    // 연결된 모든 세션을 저장할 스레드 안전한 Set
    @Getter
    private final Set<WebSocketSession> sessions = new CopyOnWriteArraySet<>();
    private final ObjectMapper objectMapper;

    @Autowired
    public ChatWebSocketHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * 메시지만 전달하는 함수
     *
     * @param chatMessage
     * @throws JsonProcessingException
     */
    public void sendMessage(ChatMessage chatMessage) throws Exception {
        this.handleTextMessage(
                null,
                new TextMessage(objectMapper.writeValueAsString(chatMessage)
                )
        );
    }

    /**
     * 새로운 세션 연결이 되었을때 실행되는 함수
     * 새로운 연결이 감지되면, 웰컴 메시지를 전달합니다
     *
     * @param session 새로운 연결 요청자
     * @throws Exception 메시지 전송 실패
     */
    @Override
    public void afterConnectionEstablished(@NonNull WebSocketSession session) throws Exception {
        sessions.add(session);

        String name = Objects.requireNonNull(session.getPrincipal()).getName() != null ? session.getPrincipal().getName() : session.getId();

        sendMessage(
                ChatMessage.builder()
                        .sender("System")
                        .content(String.format("%s님이 입장하셨습니다.", name))
                        .type(ChatMessage.MessageType.JOIN)
                        .build()
        );
    }

    /**
     * 메시지 요청이 들어오면 실행되는 함수
     * 내부로직: 연결된 모든 세션에 메시지를 전달함
     *
     * @param session 메시지 전송한 사람 (요청자)
     * @param message 메시지
     * @throws Exception 메시지 전송 실패
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 수신한 메시지를 모든 세션에 브로드캐스트
        for (WebSocketSession webSocketSession : sessions) {
            if (webSocketSession.isOpen()) {
                webSocketSession.sendMessage(message);
            }
        }
    }

    /**
     * 소켓 연결이 해제되었을 때 실행되는 함수
     *
     * @param session 연결 끊킨 사람
     * @param status  연결 상태
     * @throws Exception 메시지 전송 실패
     */
    @Override
    public void afterConnectionClosed(@NonNull WebSocketSession session, @NonNull CloseStatus status) throws Exception {
        sessions.remove(session);
        String name = Objects.requireNonNull(session.getPrincipal()).getName() != null ? session.getPrincipal().getName() : session.getId();

        sendMessage(
                ChatMessage.builder()
                        .sender("System")
                        .content(String.format("%s님이 퇴장하셨습니다.", name))
                        .type(ChatMessage.MessageType.LEAVE)
                        .build()
        );
    }
}

