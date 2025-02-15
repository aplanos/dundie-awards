package com.ninjaone.dundieawards.organization;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.retry.annotation.EnableRetry;


@EnableRetry
@SpringBootApplication
@ComponentScan(basePackages = {
        "com.ninjaone.dundieawards.organization",
        "com.ninjaone.dundieawards.messaging",
        "com.ninjaone.dundieawards.common.infrastructure.security"
})
public class OrganizationApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrganizationApplication.class, args);
    }
}
