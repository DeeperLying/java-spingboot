package com.evan.wj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class WjApplication {

    public static void main(String[] args) {
        SpringApplication.run(WjApplication.class, args);
    }

}