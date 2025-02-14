package com.ninjaone.dundieawards.activity.infrastructure.messaging.rabbitmq;

import com.ninjaone.dundieawards.common.infrastructure.utils.JsonUtils;
import com.ninjaone.dundieawards.messaging.application.messaging.MessageRouter;
import com.ninjaone.dundieawards.messaging.domain.event.activity_create.ActivityCreateEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RabbitMqActivityEventListener {

    private final MessageRouter messageRouter;

    @Autowired
    public RabbitMqActivityEventListener(MessageRouter messageRouter) {
        this.messageRouter = messageRouter;
    }

    @RabbitListener(queues = "activity-create-queue", containerFactory = "singleThreadedListenerFactory")
    public void receiveMessage(String message) {
        log.info("Processed activity create message: {}", message);
        try {
            final var model = JsonUtils.decodeFromJson(message, ActivityCreateEvent.class)
                    .orElseThrow(() -> new RuntimeException("Could not decode message: " + message));

            messageRouter.route(model);
        } catch (Exception e) {
            log.error("Error processing activity create message: {}", message, e);
        }
    }
}
