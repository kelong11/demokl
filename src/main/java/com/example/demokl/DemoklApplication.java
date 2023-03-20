package com.example.demokl;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.demokl")
public class DemoklApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoklApplication.class, args);

    }

}
