package com.ninjaone.dundieawards.organization.infraestructure.adapter.messaging.rabbitmq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ninjaone.dundieawards.common.utils.JsonUtils;
import com.ninjaone.dundieawards.organization.domain.event.increase_dundie_awards.IncreaseDundieAwardsEvent;
import com.ninjaone.dundieawards.organization.infraestructure.adapter.messaging.OrganizationEventPublisher;
import com.ninjaone.dundieawards.organization.infraestructure.adapter.messaging.rabbitmq.config.RabbitMQProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RabbitMqOrganizationEventPublisher implements OrganizationEventPublisher {
    private final RabbitTemplate rabbitTemplate;
    private final RabbitMQProperties rabbitMQProperties;
    private final MessageProperties messageProperties;

    public RabbitMqOrganizationEventPublisher(RabbitTemplate rabbitTemplate, RabbitMQProperties rabbitMQProperties) {
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitMQProperties = rabbitMQProperties;

        messageProperties = new MessageProperties();
        messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
    }

    @Override
    public void publishIncreaseDundieAwardsEvent(IncreaseDundieAwardsEvent event) {
        try {

            final Message rabbitMessage = new Message(
                    JsonUtils.encodeToJson(event).getBytes(),
                    messageProperties
            );

            rabbitTemplate.convertAndSend(
                    rabbitMQProperties.getExchangeName(),
                    rabbitMQProperties.getRoutingKey(),
                    rabbitMessage
            );
            log.info("Sent message succeed: {}", event);
        } catch (JsonProcessingException e) {
            log.error("Sent message failed: {}", event);
        }
    }
}
