package com.ninjaone.dundieawards.organization.application.api;

import com.ninjaone.dundieawards.organization.application.dto.EmployeeModel;
import com.ninjaone.dundieawards.organization.application.dto.OrganizationModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @Operation(summary = "Get all organizations", description = "Fetches all organizations with pagination",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of organizations retrieved successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PagedModel.class))),
                    @ApiResponse(responseCode = "403", description = "Forbidden - User does not have required permission")
            })
    @GetMapping
    PagedModel<OrganizationModel> getAllOrganizations(
            @Parameter(description = "The page number to fetch") @RequestParam(value = "page") @Min(0) int page,
            @Parameter(description = "The size of each page") @RequestParam(value = "pageSize", required = false, defaultValue = "10") @Min(1) @Max(100) int pageSize
    );
}
