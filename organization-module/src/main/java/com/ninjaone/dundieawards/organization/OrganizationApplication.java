package com.ninjaone.dundieawards.organization;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackages = {
        "com.ninjaone.dundieawards.organization",
        "com.ninjaone.dundieawards.messaging",
        "com.ninjaone.dundieawards.auth"
})
public class OrganizationApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrganizationApplication.class, args);
    }
}
