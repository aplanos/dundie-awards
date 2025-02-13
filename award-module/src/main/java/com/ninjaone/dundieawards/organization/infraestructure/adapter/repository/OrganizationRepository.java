package com.ninjaone.dundieawards.organization.infraestructure.adapter.repository;

import com.ninjaone.dundieawards.organization.domain.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {

}
