package com.ninjaone.dundieawards.organization.application.service;

import com.ninjaone.dundieawards.organization.application.dto.OrganizationModel;
import org.springframework.data.domain.Page;

public interface OrganizationService {

    void save(OrganizationModel organization);
    void update(OrganizationModel organization);
    void delete(OrganizationModel organization);
    Page<OrganizationModel> findAll(int page, int pageSize);
    OrganizationModel findById(long id);
    void giveAwards(long organizationId, long amount);
}
