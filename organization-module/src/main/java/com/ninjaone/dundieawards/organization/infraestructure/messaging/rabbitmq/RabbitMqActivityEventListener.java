package com.ninjaone.dundieawards.organization.infraestructure.messaging.rabbitmq;

import com.ninjaone.dundieawards.common.infraestructure.utils.JsonUtils;
import com.ninjaone.dundieawards.messaging.application.messaging.MessageRouter;
import com.ninjaone.dundieawards.messaging.domain.event.activity_rollback.ActivityRollBackEvent;
import com.ninjaone.dundieawards.messaging.domain.event.increase_dundie_awards.IncreaseDundieAwardsEvent;
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

    @RabbitListener(queues = "activity-rollback-queue", containerFactory = "singleThreadedListenerFactory")
    public void receiveMessage(String message) {
        log.info("Processed Activity Rollback Message: {}", message);
        try {
            final var model = JsonUtils.decodeFromJson(message, ActivityRollBackEvent.class)
                    .orElseThrow(() -> new RuntimeException("Could not decode message: " + message));

            messageRouter.route(model);
        } catch (Exception e) {
            log.error("Error processing ordered message: {}", message, e);
        }
    }
}
