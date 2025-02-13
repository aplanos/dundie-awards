package com.ninjaone.dundieawards.organization.infraestructure.adapter.messaging.broker.config;

import com.ninjaone.dundieawards.organization.infraestructure.adapter.messaging.broker.MessageBroker;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageBrokerConfig {

    // Conditionally create the MessageBroker bean
    @Bean
    @ConditionalOnProperty(name = "messagging.strategy", havingValue = "MESSAGE_BROKER")
    public MessageBroker messageBroker() {
        return new MessageBroker();
    }
}
