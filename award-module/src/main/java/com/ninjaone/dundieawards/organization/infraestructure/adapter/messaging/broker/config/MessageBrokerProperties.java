package com.ninjaone.dundieawards.organization.infraestructure.adapter.messaging.broker.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter @Setter
@Configuration
@ConfigurationProperties(prefix = "messaging.messagebroker")
public class MessageBrokerProperties {
    private long pollingInterval;
}
