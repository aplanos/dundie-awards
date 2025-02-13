package com.ninjaone.dundieawards.organization.application.service.impl;

import com.ninjaone.dundieawards.common.utils.MapperUtils;
import com.ninjaone.dundieawards.organization.application.dto.ActivityModel;
import com.ninjaone.dundieawards.organization.application.service.ActivityService;
import com.ninjaone.dundieawards.organization.domain.entity.Activity;
import com.ninjaone.dundieawards.organization.domain.specification.TenantSpecification;
import com.ninjaone.dundieawards.organization.infraestructure.adapter.repository.ActivityRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class ActivityServiceImpl implements ActivityService {

    private final ActivityRepository activityRepository;
    private final ModelMapper modelMapper = MapperUtils.getDefaultMapper();

    public ActivityServiceImpl(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    @Override
    public Page<ActivityModel> findAllByOrganizationId(int page, int pageSize, long organizationId) {
        return findAll(page, pageSize,
                Arrays.asList("organizationId", "type"),
                TenantSpecification.hasOrganizationId(organizationId)
        );
    }

    @Override
    public Page<ActivityModel> findAll(int page, int pageSize) {
        return findAll(page, pageSize, Collections.singletonList("id"), null);
    }

    private Page<ActivityModel> findAll(int page, int pageSize, List<String> sortKeys, Specification<Activity> specification) {
        var pageRequest = PageRequest.of(page, pageSize,
                Sort.by(Sort.Direction.ASC, sortKeys.toArray(new String[0]))
        );

        final var employees = specification == null
                ? activityRepository.findAll(pageRequest)
                : activityRepository.findAll(specification, pageRequest);

        var models =  employees.getContent()
                .stream()
                .map(e -> modelMapper.map(e, ActivityModel.class))
                .toList();

        return new PageImpl<>(models, pageRequest, employees.getTotalElements());
    }

    @Override
    public ActivityModel save(ActivityModel model) {
        final var activity = activityRepository.save(
                MapperUtils.mapTo(model, Activity.class)
        );

        return MapperUtils.mapTo(activity, ActivityModel.class);
    }

    @Override
    public ActivityModel findById(long id) {

        final var activity = activityRepository.findById(id).
                orElseThrow(() -> new IllegalArgumentException("Activity not found"));

        return MapperUtils.mapTo(activity, ActivityModel.class);
    }

    @Override
    public void delete(long id) {
        activityRepository.deleteById(id);
    }
}
