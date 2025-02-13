package com.ninjaone.dundieawards.organization.infraestructure.adapter.rest;

import com.ninjaone.dundieawards.organization.application.dto.ActivityModel;
import com.ninjaone.dundieawards.organization.application.dto.AwardSummaryStats;
import com.ninjaone.dundieawards.organization.application.dto.EmployeeModel;
import com.ninjaone.dundieawards.organization.application.service.ActivityService;
import com.ninjaone.dundieawards.organization.application.service.EmployeeService;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/activities/v1")
public class ActivityController {

    private final ActivityService activityService;

    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('activity_manage')")
    public PagedModel<ActivityModel> getAllActivities(@RequestParam(value = "page") int page,
                                                      @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {
        return new PagedModel<>(activityService.findAll(page, pageSize));
    }
}