package com.inspire12.likelionwebsocket.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Builder
public class ChatMessage {

    public enum MessageType {
        CHAT,
        JOIN,
        LEAVE,
        SYSTEM
    }

    @Getter
    private MessageType type;
    @Getter
    private String content;
    @Getter
    private String sender;

    public static ChatMessage createWelcomeMessage(String sender) {
        ChatMessage welcomeMessage = ChatMessage.builder()
                .sender("System")
                .content(
                        String.format("""
                        %s 님이 들어왔습니다.
                        """, sender))
                .type(ChatMessage.MessageType.JOIN)
                .build();

        return welcomeMessage;
    }

    public static ChatMessage createSystemMessage(ChatMessage chatMessage) {
        ChatMessage systemMessage = ChatMessage.builder()
                .sender("System")
                .content("안녕하세요 어서오세요")
                .type(chatMessage.getType())
                .build();

        return systemMessage;
    }
}
