package com.ninjaone.dundieawards.organization.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeModel {
    @Schema(description = "Unique identifier of the employee", example = "1")
    @NotNull(message = "ID cannot be null")
    private Long id;

    @Schema(description = "First name of the employee", example = "John")
    @NotNull(message = "First name cannot be null")
    @Size(min = 1, max = 50, message = "First name must be between 1 and 50 characters")
    private String firstName;

    @Schema(description = "Last name of the employee", example = "Doe")
    @NotNull(message = "Last name cannot be null")
    @Size(min = 1, max = 50, message = "Last name must be between 1 and 50 characters")
    private String lastName;

    @Schema(description = "Number of Dundie awards the employee has received", example = "5")
    @NotNull(message = "Dundie Awards cannot be null")
    private Long dundieAwards;

    @Schema(description = "Identifier of the organization the employee belongs to", example = "100")
    @NotNull(message = "Organization ID cannot be null")
    private Long organizationId;

    @Schema(description = "Name of the organization the employee belongs to", example = "100")
    private String organizationName;
}
