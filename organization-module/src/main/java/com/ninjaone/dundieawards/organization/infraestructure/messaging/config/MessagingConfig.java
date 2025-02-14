package com.ninjaone.dundieawards.organization.infraestructure.messaging.config;

import com.ninjaone.dundieawards.messaging.application.messaging.publishers.OrganizationEventPublisher;
import com.ninjaone.dundieawards.organization.infraestructure.messaging.broker.MessageBrokerOrganizationEventPublisher;
import com.ninjaone.dundieawards.organization.infraestructure.messaging.rabbitmq.RabbitMqOrganizationEventPublisher;
import com.ninjaone.dundieawards.organization.infraestructure.messaging.model.MessagingProvider;
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
