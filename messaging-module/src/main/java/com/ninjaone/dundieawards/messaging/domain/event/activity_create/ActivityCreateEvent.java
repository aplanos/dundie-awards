package com.ninjaone.dundieawards.messaging.domain.event.activity_create;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.ninjaone.dundieawards.messaging.domain.event.DomainEvent;
import com.ninjaone.dundieawards.messaging.domain.event.DomainEventType;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "version")
@JsonSubTypes({
        @JsonSubTypes.Type(value = ActivityCreateEventV1.class, name = "1")
})
public sealed interface ActivityCreateEvent extends DomainEvent permits ActivityCreateEventV1 {
    default DomainEventType type() {
        return DomainEventType.ACTIVITY_CREATE;
    }
}


