package com.inspire12.likelionwebsocket.controller;

import com.inspire12.likelionwebsocket.model.ChatMessage;
import com.inspire12.likelionwebsocket.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {

    @Autowired
    private MessageService messageService;

    @PostMapping("/broadcasting")
    public HttpStatus systemChat(@RequestBody String message){
        return messageService.createSystemMessage(message) ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR;

    }


}
