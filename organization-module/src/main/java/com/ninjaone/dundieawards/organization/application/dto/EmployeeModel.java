package com.ninjaone.dundieawards.organization.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeModel {
    private long id;
    private String firstName;
    private String lastName;
    private long dundieAwards;
    private long organizationId;
}
