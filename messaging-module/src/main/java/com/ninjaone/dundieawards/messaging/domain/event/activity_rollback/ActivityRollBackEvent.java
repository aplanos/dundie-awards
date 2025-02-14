package com.ninjaone.dundieawards.messaging.domain.event.activity_rollback;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.ninjaone.dundieawards.messaging.domain.event.DomainEvent;
import com.ninjaone.dundieawards.messaging.domain.event.DomainEventType;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "version")
@JsonSubTypes({
        @JsonSubTypes.Type(value = ActivityRollBackEventV1.class, name = "1")
})
public sealed interface ActivityRollBackEvent extends DomainEvent permits ActivityRollBackEventV1 {
    default DomainEventType type() {
        return DomainEventType.ACTIVITY_ROLLBACK;
    }
}


