package com.ninjaone.dundieawards.organization.domain.entity;

import com.ninjaone.dundieawards.organization.domain.enums.JobStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "quartz_jobs")
@Getter @Setter
@NoArgsConstructor
public class QuartzJob {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "job_name")
    private String jobName;

    @Column(name = "cron_expression")
    private String cronExpression;

    @Column(name = "job_status")
    @Enumerated(EnumType.STRING)
    private JobStatus jobStatus;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    public QuartzJob(String jobName, String cronExpression) {
        this.jobName = jobName;
        this.cronExpression = cronExpression;
        this.jobStatus = JobStatus.PENDING;
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }
}
