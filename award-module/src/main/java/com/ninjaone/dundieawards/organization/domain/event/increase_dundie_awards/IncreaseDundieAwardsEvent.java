package com.ninjaone.dundieawards.organization.domain.event.increase_dundie_awards;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.ninjaone.dundieawards.organization.domain.event.DomainEvent;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "version")
@JsonSubTypes({
        @JsonSubTypes.Type(value = IncreaseDundieAwardsEventV1.class, name = "1")
})
public sealed interface IncreaseDundieAwardsEvent extends DomainEvent permits IncreaseDundieAwardsEventV1 {}


