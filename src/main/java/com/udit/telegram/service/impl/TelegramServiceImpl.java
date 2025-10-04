package com.udit.telegram.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.udit.telegram.client.TelegramFeignClient;
import com.udit.telegram.service.TelegramService;

import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TelegramServiceImpl implements TelegramService {
    
    @Value("${telegram.bot.chat_id}")
    private String DEFAULT_CHAT_ID;

    private final TelegramFeignClient telegramFeignClient;

    @Override
    public String sendMessage(String chatId, String messageText) {
        return telegramFeignClient.sendMessage(StringUtils.isEmpty(chatId) ? DEFAULT_CHAT_ID : chatId, messageText);
    }

    @Override
    public String processRequest(String chatId, String message) {
        return "Unimplemented method 'processRequest'";
    }
}
