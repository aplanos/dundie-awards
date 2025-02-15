package com.ninjaone.dundieawards.organization.infrastructure.repository.projection;


public interface EmployeeListItemProjection {
    Long getId();
    String getFirstName();
    String getLastName();
    Long getDundieAwards();
    Long getOrganizationId();
    String getOrganizationName();
}
