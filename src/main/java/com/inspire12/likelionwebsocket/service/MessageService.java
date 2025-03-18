package com.inspire12.likelionwebsocket.service;

import com.inspire12.likelionwebsocket.model.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MessageService {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final AiService aiService;

    public ChatMessage createWelcomeMessage(ChatMessage chatMessage) {
        ChatMessage welcomeMessage = ChatMessage.builder()
                .sender("System")
                .content(
                        String.format("""
                        %s 님이 들어왔습니다.
                        """, chatMessage.getSender()))
                .type(ChatMessage.MessageType.JOIN)
                .build();

        return welcomeMessage;
    }

    public void sendMessage(ChatMessage chatMessage) {
        simpMessagingTemplate.convertAndSend("/topic/public", chatMessage);
        simpMessagingTemplate.convertAndSend("/topic/public", sendMessageWithAI(chatMessage));
    }

    public ChatMessage sendMessageWithAI(ChatMessage chatMessage) {
        ChatMessage aiChatMessage = ChatMessage.builder()
                .sender("AI")
                .content(aiService.getChatbotResponse(chatMessage.getContent()))
                .type(ChatMessage.MessageType.CHAT)
                .build();
        return aiChatMessage;
    }
}
