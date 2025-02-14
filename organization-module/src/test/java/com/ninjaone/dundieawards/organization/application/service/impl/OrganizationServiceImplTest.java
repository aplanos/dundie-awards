package com.ninjaone.dundieawards.organization.application.service.impl;

import com.ninjaone.dundieawards.common.infrastructure.utils.MapperUtils;
import com.ninjaone.dundieawards.messaging.application.messaging.publishers.OrganizationEventPublisher;
import com.ninjaone.dundieawards.messaging.domain.event.increase_dundie_awards.IncreaseDundieAwardsEventV1;
import com.ninjaone.dundieawards.organization.application.dto.OrganizationModel;
import com.ninjaone.dundieawards.organization.domain.entity.Organization;
import com.ninjaone.dundieawards.organization.infrastructure.repository.OrganizationRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class OrganizationServiceImplTest {

    @Mock
    private OrganizationRepository organizationRepository;

    @Mock
    private OrganizationEventPublisher organizationEventPublisher;

    @InjectMocks
    private OrganizationServiceImpl organizationService;

    private OrganizationModel organizationModel;
    private long organizationId = 1L;

    @BeforeEach
    void setUp() {
        organizationModel = new OrganizationModel();
        organizationModel.setId(organizationId);
        organizationModel.setName("Test Organization");
    }

    @Test
    void testFindById_OrganizationFound() {
        // Arrange
        when(organizationRepository.findById(anyLong())).thenReturn(Optional.of(MapperUtils.mapTo(organizationModel, Organization.class)));

        // Act
        OrganizationModel result = organizationService.findById(organizationId);

        // Assert
        assertNotNull(result);
        assertEquals(organizationId, result.getId());
        verify(organizationRepository, times(1)).findById(organizationId);
    }

    @Test
    void testFindById_OrganizationNotFound() {
        // Arrange
        when(organizationRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, () -> organizationService.findById(organizationId));
        assertEquals("Organization with id 1 not found", thrown.getMessage());
    }

    @Test
    void testGiveAwards() {
        // Arrange
        long amount = 100L;
        UUID mockedUUID = UUID.randomUUID();

        // Mock the static method create using mockStatic
        try (var mock = mockStatic(IncreaseDundieAwardsEventV1.class)) {
            IncreaseDundieAwardsEventV1 mockedEvent = mock(IncreaseDundieAwardsEventV1.class);

            // Define the behavior of the mocked static method
            mock.when(() -> IncreaseDundieAwardsEventV1.create(anyString(), anyLong(), anyLong()))
                    .thenReturn(mockedEvent);

            // Act
            organizationService.giveAwards(organizationId, amount);

            // Assert: Verify the static method was called
            mock.verify(() -> IncreaseDundieAwardsEventV1.create("awards-module", organizationId, amount), times(1));

            // Assert: Verify the event publisher method was called
            verify(organizationEventPublisher, times(1)).publishIncreaseDundieAwardsEvent(mockedEvent);
        }
    }
}
