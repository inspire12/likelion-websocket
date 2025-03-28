package com.inspire12.likelionwebsocket.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.inspire12.likelionwebsocket.model.ChatMessage;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.PongMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.security.Principal;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
@RequiredArgsConstructor
public class ChatWebSocketHandler extends TextWebSocketHandler {
    // 연결된 모든 세션을 저장할 스레드 안전한 Set
	private final Set<WebSocketSession> sessions = new CopyOnWriteArraySet<>();
	private final ObjectMapper mapper;

	// 연결 시
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		sessions.add(session);
	}

	// 메세지 핸들러
	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		// 메세지를 ChatMessage로 변환
		ChatMessage chatMessage = mapper.readValue(message.getPayload(), ChatMessage.class);
		TextMessage textMessage;
		if (chatMessage.getType().equals(ChatMessage.MessageType.JOIN)) {
			textMessage = new TextMessage(mapper.writeValueAsString(ChatMessage.createWelcomeMessage(chatMessage.getSender())));
		} else {
			textMessage = message;
		}

		// 모든 세션에 메세지 전송
		for (WebSocketSession webSocketSession : sessions) {
			webSocketSession.sendMessage(textMessage);
		}

	}

	// 끊겼을 시
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		sessions.remove(session);
	}

}

