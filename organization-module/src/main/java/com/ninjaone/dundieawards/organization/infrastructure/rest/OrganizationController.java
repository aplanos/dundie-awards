package com.ninjaone.dundieawards.organization.infrastructure.rest;

import com.ninjaone.dundieawards.organization.application.api.OrganizationControllerApi;
import com.ninjaone.dundieawards.organization.application.service.OrganizationService;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
public class OrganizationController implements OrganizationControllerApi {

    private final OrganizationService organizationService;

    public OrganizationController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @PostMapping(value = "/give-dundie-awards/{organizationId}")
    public ResponseEntity<?> giveDundieAwards(@PathVariable @Positive Long organizationId) {

        organizationService.giveAwards(organizationId, 1);

        return ResponseEntity.noContent().build();
    }
}
