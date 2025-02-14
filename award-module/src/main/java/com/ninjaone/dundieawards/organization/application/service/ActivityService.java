package com.ninjaone.dundieawards.organization.application.service;

import com.ninjaone.dundieawards.organization.application.dto.ActivityModel;
import com.ninjaone.dundieawards.organization.domain.enums.ActivityStatus;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface ActivityService {

    Page<ActivityModel> findAllByOrganizationId(int page, int pageSize, long organizationId);
    Page<ActivityModel> findAll(int page, int pageSize);
    void save(ActivityModel employee);
    ActivityModel findById(UUID id);
    void updateActivityStatus(UUID id, ActivityStatus status);
    void delete(UUID id);
}
