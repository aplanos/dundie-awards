package com.ninjaone.dundieawards.messaging.domain.event;

import java.time.Instant;
import java.util.UUID;

public interface DomainEvent {
    UUID id();
    Instant occurredOn();
    String sender();
    DomainEventType type();
}
