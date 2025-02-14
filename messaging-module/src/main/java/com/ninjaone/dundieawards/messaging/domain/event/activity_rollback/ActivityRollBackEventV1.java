package com.ninjaone.dundieawards.messaging.domain.event.activity_rollback;

import java.time.Instant;
import java.util.UUID;

public record ActivityRollBackEventV1(UUID id,
                                      String sender,
                                      Instant occurredOn,
                                      Long organizationId,
                                      Long amount) implements ActivityRollBackEvent {

    public static ActivityRollBackEventV1 create(String sender, Long organizationId, Long amount) {
        return new ActivityRollBackEventV1(UUID.randomUUID(), sender, Instant.now(), organizationId, amount);
    }
}
