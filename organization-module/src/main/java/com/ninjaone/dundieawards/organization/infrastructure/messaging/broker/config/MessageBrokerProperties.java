package com.ninjaone.dundieawards.organization.infrastructure.messaging.broker.config;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Validated
@Getter @Setter
@Configuration
@ConfigurationProperties(prefix = "messaging.messagebroker")
public class MessageBrokerProperties {
    @NotBlank(message = "Polling interval must not be blank")
    private String pollingInterval;
    @Min(value = 1, message = "Handlers batch size must be at least 1")
    @Max(value = 200, message = "Handlers batch size cannot be more than 200")
    private int handlersBatchSize;
}
