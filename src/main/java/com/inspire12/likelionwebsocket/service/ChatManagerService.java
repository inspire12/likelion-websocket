package com.inspire12.likelionwebsocket.service;

import com.inspire12.likelionwebsocket.model.ChatSessionManagerMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ChatManagerService {

    private final ChatWebSocketHandler chatWebSocketHandler;

    public ChatSessionManagerMessage getMessage() {
        return new ChatSessionManagerMessage(chatWebSocketHandler.getSessions().size());
    }
}
