package com.ninjaone.dundieawards.organization.infraestructure.messaging.broker;

import com.ninjaone.dundieawards.messaging.application.messaging.publishers.OrganizationEventPublisher;
import com.ninjaone.dundieawards.messaging.domain.event.increase_dundie_awards.IncreaseDundieAwardsEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MessageBrokerOrganizationEventPublisher implements OrganizationEventPublisher {
    private final MessageBroker messageBroker;

    public MessageBrokerOrganizationEventPublisher(MessageBroker messageBroker) {
        this.messageBroker = messageBroker;
    }

    @Override
    public void publishIncreaseDundieAwardsEvent(IncreaseDundieAwardsEvent event) {
        messageBroker.sendMessage(event);
        log.info("Sent message succeed: {}", event);
    }
}
