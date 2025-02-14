package com.ninjaone.dundieawards.organization.application.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Organization API", description = "Operations related to organization management")
@RequestMapping("/organizations/v1")
@Validated
public interface OrganizationControllerApi {

    @Operation(summary = "Give Dundie Awards", description = "Assign Dundie awards to the specified organization")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Awards successfully given to the organization"),
            @ApiResponse(responseCode = "400", description = "Invalid organization ID provided"),
            @ApiResponse(responseCode = "404", description = "Organization not found")
    })
    @PostMapping(value = "/give-dundie-awards/{organizationId}")
    ResponseEntity<?> giveDundieAwards(@PathVariable @Positive Long organizationId);
}
