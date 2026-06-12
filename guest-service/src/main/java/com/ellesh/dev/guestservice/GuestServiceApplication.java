package com.ellesh.dev.guestservice;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableDiscoveryClient
@SpringBootApplication
public class GuestServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GuestServiceApplication.class, args);
    }

}
