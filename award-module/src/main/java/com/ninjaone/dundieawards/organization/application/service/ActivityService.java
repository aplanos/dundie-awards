package com.ninjaone.dundieawards.organization.application.service;

import com.ninjaone.dundieawards.organization.application.dto.ActivityModel;
import org.springframework.data.domain.Page;

public interface ActivityService {

    Page<ActivityModel> findAllByOrganizationId(int page, int pageSize, long organizationId);
    Page<ActivityModel> findAll(int page, int pageSize);
    ActivityModel save(ActivityModel employee);
    ActivityModel findById(long id);
    void delete(long id);
}
