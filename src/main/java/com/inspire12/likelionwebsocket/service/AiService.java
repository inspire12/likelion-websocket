package com.inspire12.likelionwebsocket.service;

import lombok.RequiredArgsConstructor;


import org.springframework.ai.autoconfigure.openai.OpenAiChatProperties;
import org.springframework.ai.autoconfigure.openai.OpenAiEmbeddingProperties;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.ai.chat.model.ChatModel;

@Service
public class AiService {

    @Value("${spring.ai.openai.api-key}")
    private String openAiApiKey;

    public String getChatbotResponse(String question) {
        String prompt = "You are a helpful chatbot. question is " + question;
        OpenAiApi openAiApi = OpenAiApi.builder()
                .apiKey(openAiApiKey)
                .build();
        ChatModel chatModel = OpenAiChatModel.builder()
                .openAiApi(openAiApi)
                .build();
        return ChatClient.create(chatModel)
                .prompt(prompt)
                .call()
                .content();
    }
}
