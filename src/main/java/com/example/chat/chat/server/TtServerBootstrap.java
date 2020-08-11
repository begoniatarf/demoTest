package com.example.chat.chat.server;

import org.springframework.boot.CommandLineRunner;

//@Component
public class TtServerBootstrap implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        new SimpleChatServer(9999).run();
    }
}
