package com.ninjaone.dundieawards.organization.infraestructure.adapter.messaging;

import com.ninjaone.dundieawards.organization.domain.event.increase_dundie_awards.IncreaseDundieAwardsEvent;

public interface OrganizationEventPublisher {
    void publishIncreaseDundieAwardsEvent(IncreaseDundieAwardsEvent event);
}
