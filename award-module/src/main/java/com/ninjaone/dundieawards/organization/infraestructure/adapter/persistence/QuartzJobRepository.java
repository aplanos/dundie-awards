package com.ninjaone.dundieawards.organization.infraestructure.adapter.persistence;

import com.ninjaone.dundieawards.organization.domain.entity.QuartzJob;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuartzJobRepository extends JpaRepository<QuartzJob, Long> {
    Optional<QuartzJob> findByJobName(String jobName);
}
