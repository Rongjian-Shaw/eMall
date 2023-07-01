package com.emall.order;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableRabbit
@SpringBootApplication
public class EMallOrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(EMallOrderApplication.class, args);
    }
}
