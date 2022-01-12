package com.github.ticherti.simplechat;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SimpleChatApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(SimpleChatApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
    }

}
