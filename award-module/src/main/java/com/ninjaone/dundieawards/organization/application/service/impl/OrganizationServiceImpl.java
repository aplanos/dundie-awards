package com.ninjaone.dundieawards.organization.application.service.impl;

import com.ninjaone.dundieawards.common.utils.MapperUtils;
import com.ninjaone.dundieawards.organization.application.dto.OrganizationModel;
import com.ninjaone.dundieawards.organization.application.service.OrganizationService;
import com.ninjaone.dundieawards.organization.domain.event.increase_dundie_awards.IncreaseDundieAwardsEventV1;
import com.ninjaone.dundieawards.organization.infraestructure.adapter.messaging.OrganizationEventPublisher;
import com.ninjaone.dundieawards.organization.infraestructure.adapter.repository.OrganizationRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepository organizationRepository;
    private final OrganizationEventPublisher organizationEventPublisher;

    public OrganizationServiceImpl(OrganizationRepository organizationRepository,
                                   OrganizationEventPublisher organizationEventPublisher) {
        this.organizationRepository = organizationRepository;
        this.organizationEventPublisher = organizationEventPublisher;
    }

    @Override
    public void save(OrganizationModel organization) {
    }

    @Override
    public void update(OrganizationModel organization) {

    }

    @Override
    public void delete(OrganizationModel organization) {

    }

    @Override
    public PagedModel<OrganizationModel> findAll(int page, int pageSize) {
        return null;
    }

    @Override
    public OrganizationModel findById(long id) {

        final var organization = organizationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Organization with id %s not found", id)
                ));;

        return MapperUtils.mapTo(organization, OrganizationModel.class);
    }

    @Override
    public void giveAwards(long organizationId, long amount) {

        final var event = IncreaseDundieAwardsEventV1.create(
                "awards-module", organizationId, amount
        );

        organizationEventPublisher.publishIncreaseDundieAwardsEvent(event);
    }
}
