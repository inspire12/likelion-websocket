package com.inspire12.likelionwebsocket.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.inspire12.likelionwebsocket.holder.WebSocketSessionHolder;
import com.inspire12.likelionwebsocket.model.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.PongMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.security.Principal;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@RequiredArgsConstructor
public class ChatWebSocketHandler extends TextWebSocketHandler {

  private final ObjectMapper objectMapper;

  @Override
  public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    WebSocketSessionHolder.sessions.add(session);
  }

  @Override
  public void handleTextMessage(WebSocketSession session, TextMessage message)
      throws Exception {
    ChatMessage chatMessage = objectMapper.readValue(message.getPayload(), ChatMessage.class);
    TextMessage messageToSend = message;
    if (chatMessage.getType() == ChatMessage.MessageType.JOIN) {
      ChatMessage welcomeMessage = ChatMessage.createWelcomeMessage(chatMessage.getSender());
      messageToSend = new TextMessage(objectMapper.writeValueAsBytes(welcomeMessage));
    }

    for (WebSocketSession webSocketSession : WebSocketSessionHolder.sessions) {
      if (webSocketSession.isOpen()) {
        webSocketSession.sendMessage(messageToSend);
      }
    }
  }

  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus)
      throws Exception {
    WebSocketSessionHolder.sessions.remove(session);
  }

}

