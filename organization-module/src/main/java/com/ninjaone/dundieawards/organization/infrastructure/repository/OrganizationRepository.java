package com.ninjaone.dundieawards.organization.infrastructure.repository;

import com.ninjaone.dundieawards.organization.domain.entity.Employee;
import com.ninjaone.dundieawards.organization.domain.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long>, JpaSpecificationExecutor<Organization> {

}
