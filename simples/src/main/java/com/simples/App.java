package com.simples;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import lombok.extern.log4j.Log4j2;

@SpringBootApplication
@Log4j2
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    // @Bean
    // ApplicationRunner applicationRunner (Environment environment){
    //     return args -> {
    //         log.info("Message from application properties : " + environment.getProperty("message-from-application-properties"));
    //     };
    // }
}