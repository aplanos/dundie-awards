package com.ninjaone.dundieawards.activity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.retry.annotation.EnableRetry;

@EnableRetry
@SpringBootApplication
@ComponentScan(basePackages = {
        "com.ninjaone.dundieawards.activity",
        "com.ninjaone.dundieawards.messaging",
        "com.ninjaone.dundieawards.common.infrastructure.security"
})
public class ActivityModuleApplication {

    public static void main(String[] args) {
        SpringApplication.run(ActivityModuleApplication.class, args);
    }

}
