package com.ninjaone.dundieawards.organization.infraestructure.adapter.messaging.rabbitmq;

import com.ninjaone.dundieawards.common.utils.JsonUtils;
import com.ninjaone.dundieawards.organization.application.service.messaging.MessageRouter;
import com.ninjaone.dundieawards.organization.domain.event.increase_dundie_awards.IncreaseDundieAwardsEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RabbitMqOrganizationEventListener {

    private final MessageRouter messageRouter;

    @Autowired
    public RabbitMqOrganizationEventListener(MessageRouter messageRouter) {
        this.messageRouter = messageRouter;
    }

    @RabbitListener(queues = "ordered-queue", containerFactory = "singleThreadedListenerFactory")
    public void receiveMessage(String message) {
        log.info("Processed ordered message: {}", message);
        try {
            final var model = JsonUtils.decodeFromJson(message, IncreaseDundieAwardsEvent.class)
                    .orElseThrow(() -> new RuntimeException("Could not decode message: " + message));

            messageRouter.route(model);
        } catch (Exception e) {
            log.error("Error processing ordered message: {}", message, e);
        }
    }
}
