package com.ninjaone.dundieawards.activity.infrastructure.rest;

import com.ninjaone.dundieawards.activity.application.api.ActivityApi;
import com.ninjaone.dundieawards.activity.application.dto.ActivityModel;
import com.ninjaone.dundieawards.activity.application.service.ActivityService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.data.web.PagedModel;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/activities/v1")
@Validated
public class ActivityController implements ActivityApi {

    private final ActivityService activityService;

    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @GetMapping
    public PagedModel<ActivityModel> getAllActivities(@RequestParam(value = "page") @Min(0) int page,
                                                      @RequestParam(value = "pageSize", required = false, defaultValue = "10")  @Min(1) @Max(100) int pageSize) {
        return new PagedModel<>(activityService.findAll(page, pageSize));
    }
}