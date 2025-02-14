package com.ninjaone.dundieawards.activity.infraestructure.rest;

import com.ninjaone.dundieawards.activity.application.dto.ActivityModel;
import com.ninjaone.dundieawards.activity.application.service.ActivityService;
import org.springframework.data.web.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/activities/v1")
public class ActivityController {

    private final ActivityService activityService;

    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @GetMapping
    public PagedModel<ActivityModel> getAllActivities(@RequestParam(value = "page") int page,
                                                      @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {
        return new PagedModel<>(activityService.findAll(page, pageSize));
    }
}