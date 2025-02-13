package com.ninjaone.dundieawards.organization.domain.event.increase_dundie_awards;

import com.ninjaone.dundieawards.organization.domain.event.DomainEventType;

import java.time.Instant;
import java.util.Map;

public record IncreaseDundieAwardsEventV1(String sender, Instant occurredOn, Long organizationId) implements IncreaseDundieAwardsEvent {

    public static IncreaseDundieAwardsEventV1 create(String sender, Long organizationId) {
        return new IncreaseDundieAwardsEventV1(sender, Instant.now(), organizationId);
    }

    @Override
    public DomainEventType type() {
        return DomainEventType.INCREASE_DUNDIE_AWARDS;
    }

    @Override
    public Map<String, String> body() {
        return Map.of("organizationId", String.valueOf(organizationId));

    }
}
