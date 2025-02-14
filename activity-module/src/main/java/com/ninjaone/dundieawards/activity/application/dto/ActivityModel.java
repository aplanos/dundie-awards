package com.ninjaone.dundieawards.activity.application.dto;

import com.ninjaone.dundieawards.activity.domain.ActivityStatus;
import com.ninjaone.dundieawards.activity.domain.ActivityType;
import lombok.*;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor

@Schema(name = "ActivityModel", description = "Represents an activity within the system")
public class ActivityModel {

    @Schema(description = "Unique identifier of the activity", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID id;

    @Schema(description = "ID of the organization associated with the activity", example = "123456")
    private long organizationId;

    @Schema(description = "Timestamp when the activity started", example = "2025-02-14T10:00:00")
    private Instant startedAt;

    @Schema(description = "Type of the activity", example = "USER_LOGIN")
    private ActivityType type;

    @Schema(description = "Current status of the activity", example = "IN_PROGRESS")
    private ActivityStatus status;

    @Schema(description = "Additional context information in key-value pairs", example = "{\"ip\": \"192.168.1.1\", \"device\": \"mobile\"}")
    private Map<String, String> context;

    @Schema(description = "Log message associated with the activity", example = "User logged in successfully")
    private String log;

    @Schema(description = "Timestamp when the activity was created", example = "2025-02-14T09:55:00")
    private Instant createdAt;

    @Schema(description = "Timestamp when the activity was last updated", example = "2025-02-14T09:59:00")
    private Instant updatedAt;

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
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
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
            this.updatedAt = Instant.now();
            this.startedAt = Instant.now();
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
            this.updatedAt = Instant.now();
        }
        return this;
    }
}
