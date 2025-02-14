package com.ninjaone.dundieawards.messaging.application.messaging.publishers;

import com.ninjaone.dundieawards.messaging.domain.event.activity_create.ActivityCreateEvent;
import com.ninjaone.dundieawards.messaging.domain.event.activity_rollback.ActivityRollBackEvent;

public interface ActivityEventPublisher {
    void publishActivityCreateEvent(ActivityCreateEvent event);
    void publishActivityRollBackEvent(ActivityRollBackEvent event);
}
