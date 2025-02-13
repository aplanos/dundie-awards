package com.ninjaone.dundieawards.organization.domain.event;

import java.time.Instant;
import java.util.Map;

public interface DomainEvent {
    Instant occurredOn();
    String sender();
    DomainEventType type();
    Map<String, String> body();
}
