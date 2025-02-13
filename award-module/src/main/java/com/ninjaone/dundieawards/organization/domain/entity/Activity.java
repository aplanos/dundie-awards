package com.ninjaone.dundieawards.organization.domain.entity;

import com.ninjaone.dundieawards.organization.domain.enums.ActivityType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "activities")
@Getter @Setter
@NoArgsConstructor
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "occured_at")
    private LocalDateTime occuredAt;

    @Column(name = "event")
    @Enumerated(EnumType.STRING)
    private ActivityType event;

    public Activity(LocalDateTime localDateTime, ActivityType event) {
        super();
        this.occuredAt = localDateTime;
        this.event = event;
    }
}
