package com.ninjaone.dundieawards.organization.infraestructure.rest;

import com.ninjaone.dundieawards.organization.application.service.OrganizationService;
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
