package com.udit.telegram.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.udit.telegram.service.TelegramService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("${spring.application.controllers.telegram.base-path}")
@RequiredArgsConstructor
public class TelegramController {

    private final TelegramService telegramService;

    @GetMapping("/sendMessage")
    public String sendMessage(@RequestParam(name = "chatId", required = false) String chatId,
                              @RequestParam(name = "messageText") String messageText) {
        return telegramService.sendMessage(chatId, messageText);
    }

    @PostMapping("/receiveMessage")
    public ResponseEntity<String> onUpdateReceived(@RequestBody String updateJson) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(updateJson);

            String text = root.path("message").path("text").asText();
            String chatId = root.path("message").path("from").path("id").asText();

            String response = telegramService.processRequest(chatId, text);
            sendMessage(chatId, response);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok("OK");
    }
    
}
