package com.ninjaone.dundieawards.activity.infraestructure.repository;

import com.ninjaone.dundieawards.activity.domain.ActivityStatus;
import com.ninjaone.dundieawards.activity.domain.entity.Activity;
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
    void updateActivityStatus(@Param("id") UUID id, @Param("status") ActivityStatus status);
}
