package com.example.srarsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
public class SrarsystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(SrarsystemApplication.class, args);
    }
}
