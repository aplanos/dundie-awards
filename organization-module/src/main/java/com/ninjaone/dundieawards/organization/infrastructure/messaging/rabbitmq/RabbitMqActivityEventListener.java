package com.ninjaone.dundieawards.organization.infrastructure.messaging.rabbitmq;

import com.ninjaone.dundieawards.common.infrastructure.utils.JsonUtils;
import com.ninjaone.dundieawards.messaging.application.messaging.MessageRouter;
import com.ninjaone.dundieawards.messaging.domain.event.activity_rollback.ActivityRollBackEvent;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
public class RabbitMqActivityEventListener {

    private final MessageRouter messageRouter;

    @Autowired
    public RabbitMqActivityEventListener(MessageRouter messageRouter) {
        this.messageRouter = messageRouter;
    }

    @RabbitListener(queues = "activity-rollback-queue", containerFactory = "singleThreadedListenerFactory")
    public void receiveMessage(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) {
        log.info("Processed Activity Rollback Message: {}", message);
        try {
            final var model = JsonUtils.decodeFromJson(message, ActivityRollBackEvent.class)
                    .orElseThrow(() -> new RuntimeException("Could not decode message: " + message));

            messageRouter.route(model);
        } catch (Exception e) {
            log.error("Error processing ordered message: {}", message, e);

            try {
                channel.basicNack(tag, false, false);
            } catch (IOException ioException) {
                log.error("Failed to nack message", ioException);
            }
        }
    }
}
