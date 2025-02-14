package com.ninjaone.dundieawards.organization.infrastructure.messaging.rabbitmq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ninjaone.dundieawards.common.infrastructure.utils.JsonUtils;
import com.ninjaone.dundieawards.messaging.application.messaging.publishers.ActivityEventPublisher;
import com.ninjaone.dundieawards.messaging.domain.event.DomainEvent;
import com.ninjaone.dundieawards.messaging.domain.event.activity_create.ActivityCreateEvent;
import com.ninjaone.dundieawards.messaging.domain.event.activity_rollback.ActivityRollBackEvent;
import com.ninjaone.dundieawards.organization.infrastructure.messaging.rabbitmq.config.RabbitMQProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RabbitMqActivityEventPublisher implements ActivityEventPublisher {
    private final RabbitTemplate rabbitTemplate;
    private final RabbitMQProperties rabbitMQProperties;
    private final MessageProperties messageProperties;

    public RabbitMqActivityEventPublisher(@Qualifier("activityCreateRabbitTemplate") RabbitTemplate rabbitTemplate,
                                          RabbitMQProperties rabbitMQProperties) {
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitMQProperties = rabbitMQProperties;

        messageProperties = new MessageProperties();
        messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
    }

    @Override
    public void publishActivityCreateEvent(ActivityCreateEvent event) {
        sendMessage(event, rabbitMQProperties.getActivityCreateRoutingKey());
    }

    @Override
    public void publishActivityRollBackEvent(ActivityRollBackEvent event) {
        sendMessage(event, rabbitMQProperties.getActivityRollbackRoutingKey());
    }

    private void sendMessage(DomainEvent event, String routingKey) {
        try {
            final Message rabbitMessage = new Message(
                    JsonUtils.encodeToJson(event).getBytes(),
                    messageProperties
            );

            rabbitTemplate.convertAndSend(
                    rabbitMQProperties.getActivityExchangeName(),
                    routingKey,
                    rabbitMessage
            );

            log.info("Sent message succeed: {}", event);
        } catch (JsonProcessingException e) {
            log.error("Sent message failed: {}", event, e);
        }
    }

}
