package com.inspire12.likelionwebsocket.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inspire12.likelionwebsocket.model.ChatMessage;
import com.inspire12.likelionwebsocket.service.MessageService;

import lombok.RequiredArgsConstructor;


// Rest API 호출을 위한 컨트롤러 작성
@RestController
@RequestMapping
@RequiredArgsConstructor
public class ChatController {

	// 컨트롤러에서 서비스를 사용하기 위해 서비스의 의존성 주입
	private final MessageService messageService;

	// Rest API 호출
	/*
	들어올 데이터 예시
	{
    "type": "CHAT",
    "content": "안녕하세요 여러분",
    "sender": "System"
	}
	*/
	@PostMapping("/hello")
	public ChatMessage helloMessage(@RequestBody ChatMessage chatMessage) {
		return messageService.sendToAll(chatMessage);
	}
}
