package com.ninjaone.dundieawards.organization.infrastructure.rest;

import com.ninjaone.dundieawards.organization.application.api.OrganizationControllerApi;
import com.ninjaone.dundieawards.organization.application.dto.OrganizationModel;
import com.ninjaone.dundieawards.organization.application.service.OrganizationService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import org.springframework.data.web.PagedModel;
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

    @GetMapping
    public PagedModel<OrganizationModel> getAllOrganizations(
            @RequestParam(value = "page") @Min(0) int page,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") @Min(1) @Max(100) int pageSize
    ) {
        return new PagedModel<>(organizationService.findAll(page, pageSize));
    }
}
