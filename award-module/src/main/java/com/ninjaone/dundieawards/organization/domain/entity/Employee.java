package com.ninjaone.dundieawards.organization.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "employees")
@Getter @Setter
@NoArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "dundie_awards")
    private long dundieAwards;

    @Column(name = "organization_id")
    private long organizationId;

    public Employee(String firstName, String lastName, long organizationId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.organizationId = organizationId;
        this.dundieAwards = 0L;
    }
}