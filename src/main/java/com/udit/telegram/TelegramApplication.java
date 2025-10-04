package com.udit.telegram;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.udit.telegram.client")
public class TelegramApplication {

	public static void main(String[] args) {
		SpringApplication.run(TelegramApplication.class, args);
	}

}
