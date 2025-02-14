package com.ninjaone.dundieawards.organization.infraestructure.repository;

import com.ninjaone.dundieawards.organization.domain.entity.QuartzJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuartzJobRepository extends JpaRepository<QuartzJob, Long> {
    Optional<QuartzJob> findByJobName(String jobName);
}
