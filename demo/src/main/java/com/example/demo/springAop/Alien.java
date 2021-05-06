package com.example.demo.springAop;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class Alien {

    @Bean
    public void show() {
        System.out.println("Hello World");
    }

    public String showing(String name) {
        System.out.println("Hello "+name);
        return name;
    }
}
