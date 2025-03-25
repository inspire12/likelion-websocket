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
        NEW
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

    //추가 : 시스템 메시지 포맷
    public static ChatMessage createMessage(String sender) {
        ChatMessage welcomeMessage = ChatMessage.builder()
                .sender("System")
                .content(
                        String.format("안녕하세요 여러분", sender))
                .type(ChatMessage.MessageType.CHAT)
                .build();

        return welcomeMessage;
    }
}

