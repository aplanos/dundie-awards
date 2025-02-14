package com.ninjaone.dundieawards.activity.application.service.impl;

import com.ninjaone.dundieawards.activity.application.dto.ActivityModel;
import com.ninjaone.dundieawards.activity.domain.ActivityStatus;
import com.ninjaone.dundieawards.activity.domain.ActivityType;
import com.ninjaone.dundieawards.activity.domain.entity.Activity;
import com.ninjaone.dundieawards.activity.infrastructure.repository.ActivityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ActivityServiceImplTest {

    @Mock
    private ActivityRepository activityRepository;

    @InjectMocks
    private ActivityServiceImpl activityService;

    private ActivityModel activityModel;
    private Activity activity;
    private UUID activityId;

    @BeforeEach
    void setUp() {
        activityId = UUID.randomUUID();
        activityModel = new ActivityModel();
        activityModel.setId(activityId);
        activityModel.setType(ActivityType.GIVE_ORGANIZATION_DUNDIE_AWARDS);

        activity = new Activity();
        activity.setId(activityId);
        activity.setType(ActivityType.GIVE_ORGANIZATION_DUNDIE_AWARDS);
    }

    @Test
    void testFindAllByOrganizationId() {
        // Arrange
        Page<Activity> activitiesPage = new PageImpl<>(Arrays.asList(activity));
        when(activityRepository.findAll(any(Specification.class), any(PageRequest.class))).thenReturn(activitiesPage);

        // Act
        Page<ActivityModel> result = activityService.findAllByOrganizationId(0, 10, 123L);

        // Assert
        verify(activityRepository, times(1)).findAll(any(Specification.class), any(PageRequest.class));
        assert result.getContent().size() == 1;
    }

    @Test
    void testFindAll() {
        // Arrange
        Page<Activity> activitiesPage = new PageImpl<>(Arrays.asList(activity));
        when(activityRepository.findAll(any(PageRequest.class))).thenReturn(activitiesPage);

        // Act
        Page<ActivityModel> result = activityService.findAll(0, 10);

        // Assert
        verify(activityRepository, times(1)).findAll(any(PageRequest.class));
        assert result.getContent().size() == 1;
    }

    @Test
    void testSave() {
        // Arrange
        when(activityRepository.save(any(Activity.class))).thenReturn(activity);

        // Act
        activityService.save(activityModel);

        // Assert
        verify(activityRepository, times(1)).save(any(Activity.class));
    }

    @Test
    void testFindById() {
        // Arrange
        when(activityRepository.findById(any(UUID.class))).thenReturn(Optional.of(activity));

        // Act
        ActivityModel result = activityService.findById(activityId);

        // Assert
        verify(activityRepository, times(1)).findById(any(UUID.class));
        assert result.getId().equals(activityId);
    }

    @Test
    void testFindByIdThrowsExceptionWhenNotFound() {
        // Arrange
        when(activityRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException thrown = org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> {
            activityService.findById(activityId);
        });
        assert thrown.getMessage().equals("Activity not found");
    }

    @Test
    void testUpdateActivityStatus() {
        // Arrange
        ActivityStatus status = ActivityStatus.SUCCEEDED;

        // Act
        activityService.updateActivityStatus(activityId, status);

        // Assert
        verify(activityRepository, times(1)).updateActivityStatus(any(UUID.class), eq(status));
    }

    @Test
    void testDelete() {
        // Act
        activityService.delete(activityId);

        // Assert
        verify(activityRepository, times(1)).deleteById(any(UUID.class));
    }
}
