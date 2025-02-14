package com.ninjaone.dundieawards.organization.infraestructure.adapter.rest;

import com.ninjaone.dundieawards.organization.application.service.OrganizationService;
import com.ninjaone.dundieawards.organization.domain.event.increase_dundie_awards.IncreaseDundieAwardsEventV1;
import com.ninjaone.dundieawards.organization.infraestructure.adapter.messaging.OrganizationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/organizations/v1")
public class OrganizationController {

    private final OrganizationService organizationService;

    public OrganizationController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @PostMapping(value = "/give-dundie-awards/{organizationId}")
    public ResponseEntity<?> giveDundieAwards(@PathVariable Long organizationId) {

        organizationService.giveAwards(organizationId, 1);

        return ResponseEntity.ok("");
    }
}
