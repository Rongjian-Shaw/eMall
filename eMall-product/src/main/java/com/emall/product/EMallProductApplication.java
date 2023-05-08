package com.emall.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.emall.product.dao")
@SpringBootApplication
public class EMallProductApplication {
    public static void main(String[] args) {
        SpringApplication.run(EMallProductApplication.class, args);
    }
}
