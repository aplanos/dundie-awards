package com.ninjaone.dundieawards.organization.application.dto;

import com.ninjaone.dundieawards.organization.domain.enums.ActivityStatus;
import com.ninjaone.dundieawards.organization.domain.enums.ActivityType;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActivityModel {
    private UUID id;
    private long organizationId;
    private LocalDateTime startedAt;
    private ActivityType type;
    private ActivityStatus status;
    private Map<String, String> context;
    private String log;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /**
     * Factory method to create a pending activity for giving an organization Dundie Awards.
     *
     * @param id        The UUID of the activity.
     * @param organizationId The ID of the organization.
     * @param context   The context of the activity.
     * @return A newly created ActivityModel instance.
     */
    public static ActivityModel pendingGiveOrganizationDundieAwards(UUID id, Long organizationId, Map<String, String> context) {
        return ActivityModel.builder()
                .id(id)
                .type(ActivityType.GIVE_ORGANIZATION_DUNDIE_AWARDS)
                .status(ActivityStatus.PENDING)
                .context(context)
                .organizationId(organizationId)
                .build();
    }

    /**
     * Marks the activity as running and sets the current timestamp as the start time.
     *
     * @return The updated ActivityModel instance.
     */
    public ActivityModel markAsRunning() {
        if (status != ActivityStatus.RUNNING) {
            this.status = ActivityStatus.RUNNING;
            this.updatedAt = LocalDateTime.now();
            this.startedAt = LocalDateTime.now();
        }
        return this;
    }

    /**
     * Update the activity status and sets the current timestamp as the update time.
     *
     * @return The updated ActivityModel instance.
     */
    public ActivityModel withStatus(ActivityStatus status) {
        if (this.status != status) {
            this.status = status;
            this.updatedAt = LocalDateTime.now();
        }
        return this;
    }
}
