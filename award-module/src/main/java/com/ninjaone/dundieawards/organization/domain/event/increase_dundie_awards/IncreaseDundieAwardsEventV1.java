package com.ninjaone.dundieawards.organization.domain.event.increase_dundie_awards;

import com.ninjaone.dundieawards.organization.domain.event.DomainEventType;

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

    @Override
    public DomainEventType type() {
        return DomainEventType.INCREASE_DUNDIE_AWARDS;
    }
}
