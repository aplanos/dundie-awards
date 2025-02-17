package com.ninjaone.dundieawards.organization.infrastructure.rest;


import com.ninjaone.dundieawards.organization.application.messaging.UpdateDundieAwardsMessageHandler;
import com.ninjaone.dundieawards.organization.application.service.EmployeeService;
import com.ninjaone.dundieawards.organization.application.service.OrganizationService;
import org.junit.ClassRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Disabled
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
class OrganizationControllerTest {

    @ClassRule
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:11.1")
            .withDatabaseName("integration-tests-db")
            .withUsername("sa")
            .withPassword("sa");

    @ClassRule
    public static RabbitMQContainer rabbitMQContainer = new RabbitMQContainer(
            DockerImageName.parse("rabbitmq:3.12-management")
    );

    @ClassRule
    public static GenericContainer<?> redis = new GenericContainer<>(DockerImageName.parse("redis:7.0"))
            .withExposedPorts(6379);

    static {
        rabbitMQContainer.start();
        redis.start();
    }


    @DynamicPropertySource
    static void registerProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.rabbitmq.host", rabbitMQContainer::getHost);
        registry.add("spring.rabbitmq.port", rabbitMQContainer::getAmqpPort);
        registry.add("spring.rabbitmq.username", rabbitMQContainer::getAdminUsername);
        registry.add("spring.rabbitmq.password", rabbitMQContainer::getAdminPassword);
    }

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrganizationService organizationService;

    @MockitoBean
    private EmployeeService employeeService;

    @InjectMocks
    private UpdateDundieAwardsMessageHandler updateDundieAwardsMessageHandler;

    @InjectMocks
    private OrganizationController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGiveDundieAwards_Success() throws Exception {

        Long organizationId = 1L;

        Mockito.doNothing().when(organizationService).giveAwards(anyLong(), anyLong());
        when(employeeService.findAllByOrganizationId(anyInt(), anyInt(), anyLong()))
                .thenReturn(new PageImpl<>(Collections.emptyList(), PageRequest.of(0,1, Sort.by("id")), 0));

        mockMvc.perform(post("/organizations/v1/give-dundie-awards/{organizationId}", organizationId)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer any"))
                        .andExpect(status().isNoContent());

        verify(organizationService, times(1)).giveAwards(organizationId, 1);
    }

    @Test
    void testGiveDundieAwards_InvalidOrganizationId() throws Exception {

        Long organizationId = -1L;

        Mockito.doNothing().when(organizationService).giveAwards(anyLong(), anyLong());
        when(employeeService.findAllByOrganizationId(anyInt(), anyInt(), anyLong()))
                .thenReturn(new PageImpl<>(Collections.emptyList(), PageRequest.of(0,1, Sort.by("id")), 0));

        mockMvc.perform(post("/organizations/v1/give-dundie-awards/{organizationId}", organizationId)
                .header(HttpHeaders.AUTHORIZATION, "Bearer any"))
                .andExpect(status().isBadRequest()); // Should fail due to @Positive constraint
    }
}