package com.ninjaone.dundieawards.messaging.domain.event.activity_create;

import java.time.Instant;
import java.util.UUID;

public record ActivityCreateEventV1(UUID id,
                                    String sender,
                                    Instant occurredOn,
                                    Long organizationId) implements ActivityCreateEvent {

    public static ActivityCreateEventV1 create(String sender, Long organizationId) {
        return new ActivityCreateEventV1(UUID.randomUUID(), sender, Instant.now(), organizationId);
    }
}
