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
public class OrganizationModel {

    @Schema(description = "Unique identifier of the organization", example = "1")
    @NotNull(message = "ID cannot be null")
    private Long id;

    @Schema(description = "Name of the organization", example = "Dunder Mifflin")
    @NotNull(message = "Name cannot be null")
    @Size(min = 1, max = 100, message = "Name must be between 1 and 100 characters")
    private String name;
}