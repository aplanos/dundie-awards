package com.ninjaone.dundieawards.activity.domain.entity;

import com.ninjaone.dundieawards.activity.domain.ActivityStatus;
import com.ninjaone.dundieawards.activity.domain.ActivityType;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.HashMap;
import java.util.UUID;

@Entity
@Table(name = "activities")
@Getter @Setter
@NoArgsConstructor
public class Activity {

    @Id
    private UUID id;

    @Column(name = "organization_id")
    private long organizationId;

    @Column(name = "started_at")
    private Instant startedAt;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private ActivityType type;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ActivityStatus status;

    @Type(JsonType.class)
    @Column(name = "context", columnDefinition = "jsonb")
    private HashMap<String, String> context;

    @Column(name = "log")
    private String log;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;

    public Activity(ActivityType type,
                    Long organizationId,
                    HashMap<String, String> context) {
        super();
        this.type = type;
        this.context = context;
        this.organizationId = organizationId;
        this.status = ActivityStatus.PENDING;
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }
}
