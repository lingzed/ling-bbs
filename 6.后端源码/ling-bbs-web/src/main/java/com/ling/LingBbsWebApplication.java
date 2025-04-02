package com.ling;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

@SpringBootApplication
public class LingBbsWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(LingBbsWebApplication.class, args);
    }
}
