package com.ninjaone.dundieawards.organization.infraestructure.adapter.repository;

import com.ninjaone.dundieawards.organization.domain.entity.Activity;
import com.ninjaone.dundieawards.organization.domain.enums.ActivityStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface ActivityRepository extends JpaRepository<Activity, UUID>, JpaSpecificationExecutor<Activity> {
    @Modifying
    @Query("UPDATE Activity SET status = :status WHERE id = :id")
    void updateActivityStatus(@Param("id") UUID id, @Param("status")  ActivityStatus status);
}
