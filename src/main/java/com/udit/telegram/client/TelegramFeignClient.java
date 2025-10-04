package com.udit.telegram.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "telegramClient", url = "${telegram.url}${telegram.bot.resource}${telegram.bot.token}")
public interface TelegramFeignClient {


    @GetMapping(path = "${telegram.bot.operations.sendMessage}")
    String sendMessage(@RequestParam("chat_id") String chatId, @RequestParam("text") String messageText);

}
