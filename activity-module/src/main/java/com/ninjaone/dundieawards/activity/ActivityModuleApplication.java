package com.ninjaone.dundieawards.activity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
        "com.ninjaone.dundieawards.activity",
        "com.ninjaone.dundieawards.messaging"
})
public class ActivityModuleApplication {

    public static void main(String[] args) {
        SpringApplication.run(ActivityModuleApplication.class, args);
    }

}
