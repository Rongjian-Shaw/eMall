package com.emall.member;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = "com.emall.member.feign")
@EnableDiscoveryClient
@SpringBootApplication
public class EMallMemberApplication {
    public static void main(String[] args) {
        SpringApplication.run(EMallMemberApplication.class, args);
    }
}
