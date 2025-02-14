package com.ninjaone.dundieawards.activity.application.dto;

import com.ninjaone.dundieawards.activity.domain.ActivityStatus;
import com.ninjaone.dundieawards.activity.domain.ActivityType;
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
     * @param eventId        The UUID of the source event.
     * @param organizationId The ID of the organization.
     * @param context   The context of the activity.
     * @return A newly created ActivityModel instance.
     */
    public static ActivityModel createGiveOrganizationDundieAwards(UUID eventId, Long organizationId, Map<String, String> context) {
        return ActivityModel.builder()
                .id(UUID.randomUUID())
                .type(ActivityType.GIVE_ORGANIZATION_DUNDIE_AWARDS)
                .status(ActivityStatus.SUCCEEDED)
                .context(context)
                .organizationId(organizationId)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
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
