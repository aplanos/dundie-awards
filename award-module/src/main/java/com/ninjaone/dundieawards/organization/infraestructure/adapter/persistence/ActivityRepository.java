package com.ninjaone.dundieawards.organization.infraestructure.adapter.persistence;

import com.ninjaone.dundieawards.organization.domain.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {

}
