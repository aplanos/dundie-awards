package com.ninjaone.dundieawards.organization.application.service.impl;

import com.ninjaone.dundieawards.common.infrastructure.utils.MapperUtils;
import com.ninjaone.dundieawards.messaging.application.messaging.publishers.OrganizationEventPublisher;
import com.ninjaone.dundieawards.messaging.domain.event.increase_dundie_awards.IncreaseDundieAwardsEventV1;
import com.ninjaone.dundieawards.organization.application.dto.EmployeeModel;
import com.ninjaone.dundieawards.organization.application.dto.OrganizationModel;
import com.ninjaone.dundieawards.organization.application.service.OrganizationService;
import com.ninjaone.dundieawards.organization.domain.entity.Organization;
import com.ninjaone.dundieawards.organization.infrastructure.repository.OrganizationRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class OrganizationServiceImpl implements OrganizationService {

    private final ModelMapper modelMapper = MapperUtils.getDefaultMapper();
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
    public Page<OrganizationModel> findAll(int page, int pageSize) {
        return findAll(page, pageSize, Collections.singletonList("id"), null);

    }

    private Page<OrganizationModel> findAll(int page, int pageSize, List<String> sortKeys, Specification<Organization> specification) {
        var pageRequest = PageRequest.of(page, pageSize,
                Sort.by(Sort.Direction.ASC, sortKeys.toArray(new String[0]))
        );

        final var organizations = specification == null
                ? organizationRepository.findAll(pageRequest)
                : organizationRepository.findAll(specification, pageRequest);

        var models =  organizations.getContent()
                .stream()
                .map(e -> modelMapper.map(e, OrganizationModel.class))
                .toList();

        return new PageImpl<>(models, pageRequest, organizations.getTotalElements());
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
