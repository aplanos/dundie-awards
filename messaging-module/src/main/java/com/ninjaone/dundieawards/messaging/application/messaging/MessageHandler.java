package com.ninjaone.dundieawards.messaging.application.messaging;


import com.ninjaone.dundieawards.messaging.domain.event.DomainEvent;
import com.ninjaone.dundieawards.messaging.domain.event.DomainEventType;

public interface MessageHandler {
    void handle(DomainEvent message);
    DomainEventType getSupportedType();
}

