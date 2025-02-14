package com.ninjaone.dundieawards.messaging.application.messaging.publishers;

import com.ninjaone.dundieawards.messaging.domain.event.increase_dundie_awards.IncreaseDundieAwardsEvent;

public interface OrganizationEventPublisher {
    void publishIncreaseDundieAwardsEvent(IncreaseDundieAwardsEvent event);
}
