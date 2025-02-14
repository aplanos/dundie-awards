package com.ninjaone.dundieawards.messaging.domain.event.increase_dundie_awards;

import java.time.Instant;
import java.util.UUID;

public record IncreaseDundieAwardsEventV1(UUID id,
                                          String sender,
                                          Instant occurredOn,
                                          Long organizationId,
                                          Long amount) implements IncreaseDundieAwardsEvent {

    public static IncreaseDundieAwardsEventV1 create(String sender, Long organizationId, Long amount) {
        return new IncreaseDundieAwardsEventV1(UUID.randomUUID(), sender, Instant.now(), organizationId, amount);
    }
}
