package com.ninjaone.dundieawards.organization.infrastructure.messaging.rabbitmq.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter @Setter
@Configuration
@ConfigurationProperties(prefix = "messaging.rabbitmq")
public class RabbitMQProperties {
    private String awardsExchangeName;
    private String giveAwardsQueueName;
    private String giveAwardsRoutingKey;
    private String activityExchangeName;
    private String activityCreateQueueName;
    private String activityCreateRoutingKey;
    private String activityRollbackQueueName;
    private String activityRollbackRoutingKey;
}
