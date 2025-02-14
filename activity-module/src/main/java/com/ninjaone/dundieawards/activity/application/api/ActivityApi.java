package com.ninjaone.dundieawards.activity.application.api;

import com.ninjaone.dundieawards.activity.application.dto.ActivityModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.data.web.PagedModel;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Activity API", description = "Operations related to activities")
@RequestMapping("/activities/v1")
@Validated
public interface ActivityApi {

    @Operation(
            summary = "Retrieve all activities",
            description = "Fetches a paginated list of activities.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful retrieval of activities"),
                    @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @GetMapping
    PagedModel<ActivityModel> getAllActivities(
            @Parameter(description = "Page number (zero-based index)", example = "0")
            @RequestParam(value = "page") @Min(0) int page,

            @Parameter(description = "Number of items per page", example = "10", required = false)
            @RequestParam(value = "pageSize", defaultValue = "10") @Min(1) @Max(100) int pageSize
    );
}
