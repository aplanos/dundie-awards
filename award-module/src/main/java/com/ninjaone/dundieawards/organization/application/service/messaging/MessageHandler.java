package com.ninjaone.dundieawards.organization.application.service.messaging;

import com.ninjaone.dundieawards.organization.domain.event.DomainEvent;
import com.ninjaone.dundieawards.organization.domain.event.DomainEventType;

public interface MessageHandler {
    void handle(DomainEvent message);
    DomainEventType getSupportedType();
}

