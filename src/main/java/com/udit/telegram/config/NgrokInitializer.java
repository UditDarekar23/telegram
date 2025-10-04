package com.udit.telegram.config;

import org.springframework.stereotype.Component;

import com.udit.telegram.client.TelegramFeignClient;

import lombok.RequiredArgsConstructor;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.json.JSONObject;
import org.json.JSONArray;

@Component
@RequiredArgsConstructor
public class NgrokInitializer implements CommandLineRunner {

    @Value("${spring.port}")
    private String APPLICATION_PORT;
    @Value("${ngrok.api}")
    private String NGROK_TUNNELS_API;
    
    private final TelegramFeignClient telegramFeignClient;

     @Override
    public void run(String... args) throws Exception {
        ProcessBuilder pb = new ProcessBuilder("ngrok", "http", APPLICATION_PORT);
        pb.inheritIO();
        pb.start();
        Thread.sleep(3000);
        
        URL url = new URL(NGROK_TUNNELS_API);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();

        JSONObject json = new JSONObject(content.toString());
        JSONArray tunnels = json.getJSONArray("tunnels");

        String publicUrl = null;

        for (int i = 0; i < tunnels.length(); i++) {
            JSONObject tunnel = tunnels.getJSONObject(i);
            String urlStr = tunnel.getString("public_url");
            if (urlStr.startsWith("https://")) {
                publicUrl = urlStr;
                break;
            }
        }

        if (publicUrl != null) {
            System.out.println("Ngrok public URL: " + publicUrl);
            String setWebhook = telegramFeignClient.setWebhook(publicUrl+"/telegram/receiveMessage");
            System.out.println("Ngrok public URL: " + setWebhook);
        } else {
            System.err.println("No HTTPS tunnel found.");
        }
    }
}

