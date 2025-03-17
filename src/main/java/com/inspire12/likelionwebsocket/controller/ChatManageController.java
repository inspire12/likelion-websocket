package com.inspire12.likelionwebsocket.controller;

import com.inspire12.likelionwebsocket.model.ChatSessionManagerMessage;
import com.inspire12.likelionwebsocket.service.ChatManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ChatManageController {

    private final ChatManagerService chatManagerService;

    @GetMapping("/chat/manager")
    public ChatSessionManagerMessage getChatManagerService() {
        return chatManagerService.getMessage();
    }

}
