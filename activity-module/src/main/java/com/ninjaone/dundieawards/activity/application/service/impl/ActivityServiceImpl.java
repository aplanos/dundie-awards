package com.ninjaone.dundieawards.activity.application.service.impl;

import com.ninjaone.dundieawards.activity.application.dto.ActivityModel;
import com.ninjaone.dundieawards.activity.application.service.ActivityService;
import com.ninjaone.dundieawards.activity.domain.ActivityStatus;
import com.ninjaone.dundieawards.activity.domain.entity.Activity;
import com.ninjaone.dundieawards.activity.infrastructure.repository.ActivityRepository;
import com.ninjaone.dundieawards.common.domain.specification.TenantSpecification;
import com.ninjaone.dundieawards.common.infrastructure.utils.MapperUtils;
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
import java.util.UUID;

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
                TenantSpecification.hasTenantId("organizationId", organizationId)
        );
    }

    @Override
    public Page<ActivityModel> findAll(int page, int pageSize) {
        return findAll(page, pageSize, Collections.singletonList("updatedAt"), null);
    }

    private Page<ActivityModel> findAll(int page, int pageSize, List<String> sortKeys, Specification<Activity> specification) {
        var pageRequest = PageRequest.of(page, pageSize,
                Sort.by(Sort.Direction.DESC, sortKeys.toArray(new String[0]))
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
    public void save(ActivityModel model) {
        final var activity = activityRepository.save(
                MapperUtils.mapTo(model, Activity.class)
        );

        MapperUtils.mapTo(activity, ActivityModel.class);
    }

    @Override
    public ActivityModel findById(UUID id) {

        final var activity = activityRepository.findById(id).
                orElseThrow(() -> new IllegalArgumentException("Activity not found"));

        return MapperUtils.mapTo(activity, ActivityModel.class);
    }

    @Override
    public void updateActivityStatus(UUID id, ActivityStatus status) {
        activityRepository.updateActivityStatus(id, status);
    }

    @Override
    public void delete(UUID id) {
        activityRepository.deleteById(id);
    }
}
