package com.ninjaone.dundieawards.organization.infrastructure.messaging.config;

import com.ninjaone.dundieawards.organization.infrastructure.messaging.model.MessagingProvider;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter @Setter
@Configuration
@ConfigurationProperties(prefix = "messaging")
public class MessagingProperties {
    private MessagingProvider strategy;
}
