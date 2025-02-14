package com.ninjaone.dundieawards.organization.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class AwardSummaryStats implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "Total number of Dundie awards given", example = "150")
    @NotNull(message = "Total Dundie Awards cannot be null")
    private Long totalDundieAwards;
}
