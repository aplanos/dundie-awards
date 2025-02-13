package com.ninjaone.dundieawards.organization.infraestructure.adapter.messaging.config;

import com.ninjaone.dundieawards.organization.infraestructure.adapter.messaging.OrganizationEventPublisher;
import com.ninjaone.dundieawards.organization.infraestructure.adapter.messaging.broker.MessageBrokerOrganizationEventPublisher;
import com.ninjaone.dundieawards.organization.infraestructure.adapter.messaging.rabbitmq.RabbitMqOrganizationEventPublisher;
import com.ninjaone.dundieawards.organization.infraestructure.adapter.messaging.model.MessagingProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
public class MessagingConfig {

    private final MessagingProperties messagingProperties;

    public MessagingConfig(MessagingProperties messagingProperties) {
        this.messagingProperties = messagingProperties;
    }

    @Bean
    public OrganizationEventPublisher organizationEventPublisher(RabbitMqOrganizationEventPublisher rabbitMqPublisher,
                                                                 MessageBrokerOrganizationEventPublisher messageBrokerOrganizationEventPublisher) {

        if (Objects.requireNonNull(messagingProperties.getStrategy()) == MessagingProvider.RABBIT_MQ) {
            return rabbitMqPublisher;
        } else if (Objects.requireNonNull(messagingProperties.getStrategy()) == MessagingProvider.MESSAGE_BROKER) {
            return messageBrokerOrganizationEventPublisher;
        }

        throw new IllegalArgumentException("Unknown messaging provider: " + messagingProperties.getStrategy());
    }
}
