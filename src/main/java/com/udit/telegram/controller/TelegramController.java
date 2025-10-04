package com.udit.telegram.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.udit.telegram.service.TelegramService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
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
    
}
