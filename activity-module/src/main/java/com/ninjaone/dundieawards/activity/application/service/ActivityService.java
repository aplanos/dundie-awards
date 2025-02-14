package com.ninjaone.dundieawards.activity.application.service;

import com.ninjaone.dundieawards.activity.application.dto.ActivityModel;
import com.ninjaone.dundieawards.activity.domain.ActivityStatus;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface ActivityService {

    Page<ActivityModel> findAllByOrganizationId(int page, int pageSize, long organizationId);
    Page<ActivityModel> findAll(int page, int pageSize);
    void save(ActivityModel model);
    ActivityModel findById(UUID id);
    void updateActivityStatus(UUID id, ActivityStatus status);
    void delete(UUID id);
}
