package com.ninjaone.dundieawards.organization.infraestructure.adapter.rest;

import com.ninjaone.dundieawards.organization.domain.event.increase_dundie_awards.IncreaseDundieAwardsEventV1;
import com.ninjaone.dundieawards.organization.infraestructure.adapter.messaging.OrganizationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/organizations/v1")
public class OrganizationController {

    private final OrganizationEventPublisher organizationEventPublisher;

    public OrganizationController(OrganizationEventPublisher organizationEventPublisher) {
        this.organizationEventPublisher = organizationEventPublisher;
    }


    @PostMapping(value = "/give-dundie-awards/{organizationId}")
    public ResponseEntity<?> giveDundieAwards(@PathVariable Long organizationId) {
        organizationEventPublisher.publishIncreaseDundieAwardsEvent(
                IncreaseDundieAwardsEventV1.create(
                        "awards-module", organizationId
                )
        );

        return ResponseEntity.ok("");
    }
}
