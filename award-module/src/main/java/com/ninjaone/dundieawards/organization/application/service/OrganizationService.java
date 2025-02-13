package com.ninjaone.dundieawards.organization.application.service;

import com.ninjaone.dundieawards.organization.application.dto.OrganizationModel;
import org.springframework.data.web.PagedModel;

public interface OrganizationService {

    void save(OrganizationModel organization);
    void update(OrganizationModel organization);
    void delete(OrganizationModel organization);
    PagedModel<OrganizationModel> findAll(int page, int pageSize);
    OrganizationModel findById(long id);
}
