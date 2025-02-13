package com.ninjaone.dundieawards.organization.application.dto;

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

    private long totalDundieAwards;
}
