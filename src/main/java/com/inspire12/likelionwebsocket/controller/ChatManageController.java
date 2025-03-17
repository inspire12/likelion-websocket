package com.inspire12.likelionwebsocket.controller;

import com.inspire12.likelionwebsocket.model.ChatMessage;
import com.inspire12.likelionwebsocket.model.ChatSessionManagerMessage;
import com.inspire12.likelionwebsocket.service.ChatManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class ChatManageController {

    private final ChatManagerService chatManagerService;

    @GetMapping("/chat/manager")
    public ChatSessionManagerMessage getChatManagerService() {
        return chatManagerService.getMessage();
    }

    @GetMapping("/chat/send")
    public ChatSessionManagerMessage sendChatManagerService(@RequestBody ChatMessage chatMessage) throws IOException {
        return chatManagerService.sendAllMessage(chatMessage);
    }

}
