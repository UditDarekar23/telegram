package com.udit.telegram.service;

public interface TelegramService {
        
    String sendMessage(String chatId, String message);
}
