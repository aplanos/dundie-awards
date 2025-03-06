package com.ninjaone.dundieawards.organization.application.api;

import com.ninjaone.dundieawards.organization.application.dto.AwardSummaryStats;
import com.ninjaone.dundieawards.organization.application.dto.EmployeeModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "Employee API", description = "Operations related to employees management")
@RequestMapping("/employees/v1")
@Validated
public interface EmployeeApi {

    @Operation(summary = "Get all employees", description = "Fetches all employees with pagination",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of employees retrieved successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PagedModel.class))),
                    @ApiResponse(responseCode = "403", description = "Forbidden - User does not have required permission")
            })
    @GetMapping
    PagedModel<EmployeeModel> getAllEmployees(
            @Parameter(description = "The page number to fetch") @RequestParam(value = "page") @Min(0) int page,
            @Parameter(description = "The size of each page") @RequestParam(value = "pageSize", required = false, defaultValue = "10") @Min(1) @Max(100) int pageSize
    );

    @Operation(summary = "Get award summary stats", description = "Fetches total employee awards summary",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Award summary stats retrieved successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AwardSummaryStats.class))),
                    @ApiResponse(responseCode = "403", description = "Forbidden - User does not have required permission")
            })
    @GetMapping("/awards-summary-stats")
    AwardSummaryStats getAwardSummaryStats();

    @Operation(summary = "Create a new employee", description = "Creates a new employee record",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Employee created successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = EmployeeModel.class))),
                    @ApiResponse(responseCode = "400", description = "Bad Request - Invalid input")
            })
    @PostMapping
    EmployeeModel createEmployee(
            @Parameter(description = "Employee details to be created") @RequestBody @Valid EmployeeModel employee
    );

    @Operation(summary = "Get employee by ID", description = "Fetches employee details by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Employee retrieved successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = EmployeeModel.class))),
                    @ApiResponse(responseCode = "404", description = "Employee not found")
            })
    @GetMapping("/{id}")
    ResponseEntity<EmployeeModel> getEmployeeById(
            @Parameter(description = "ID of the employee to be fetched") @PathVariable @Positive Long id
    );

    @Operation(summary = "Update employee by ID", description = "Updates the employee details by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Employee updated successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = EmployeeModel.class))),
                    @ApiResponse(responseCode = "404", description = "Employee not found"),
                    @ApiResponse(responseCode = "400", description = "Bad Request - Invalid input")
            })
    @PutMapping("/{id}")
    ResponseEntity<EmployeeModel> updateEmployee(
            @Parameter(description = "ID of the employee to be updated") @PathVariable @Positive Long id,
            @Parameter(description = "Updated employee details") @RequestBody @Valid EmployeeModel employeeDetails
    );

    @Operation(summary = "Delete employee by ID", description = "Deletes an employee by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Employee deleted successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Map.class))),
                    @ApiResponse(responseCode = "404", description = "Employee not found")
            })
    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteEmployee(
            @Parameter(description = "ID of the employee to be deleted") @PathVariable @Positive Long id
    );
}

