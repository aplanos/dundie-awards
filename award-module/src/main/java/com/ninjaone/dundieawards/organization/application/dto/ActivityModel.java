package com.ninjaone.dundieawards.organization.application.dto;

import com.ninjaone.dundieawards.organization.domain.enums.ActivityStatus;
import com.ninjaone.dundieawards.organization.domain.enums.ActivityType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashMap;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActivityModel {
    private long id;
    private long organizationId;
    private LocalDateTime startedAt;
    private ActivityType type;
    private ActivityStatus status;
    private HashMap<String, String> context;
    private String log;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
