package com.ninjaone.dundieawards.organization.application.messaging;

import com.ninjaone.dundieawards.messaging.application.messaging.MessageHandler;
import com.ninjaone.dundieawards.messaging.application.messaging.publishers.ActivityEventPublisher;
import com.ninjaone.dundieawards.messaging.domain.event.DomainEvent;
import com.ninjaone.dundieawards.messaging.domain.event.DomainEventType;
import com.ninjaone.dundieawards.messaging.domain.event.activity_create.ActivityCreateEventV1;
import com.ninjaone.dundieawards.messaging.domain.event.activity_rollback.ActivityRollBackEventV1;
import com.ninjaone.dundieawards.messaging.domain.event.increase_dundie_awards.IncreaseDundieAwardsEventV1;
import com.ninjaone.dundieawards.organization.application.dto.EmployeeModel;
import com.ninjaone.dundieawards.organization.application.service.EmployeeService;
import com.ninjaone.dundieawards.organization.infrastructure.messaging.broker.config.MessageBrokerProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ActivityRollbackMessageHandler implements MessageHandler {

    private final MessageBrokerProperties properties;
    private final EmployeeService employeeService;

    public ActivityRollbackMessageHandler(MessageBrokerProperties properties,
                                          EmployeeService employeeService) {
        this.properties = properties;
        this.employeeService = employeeService;
    }

    @Recover
    void recover(Exception e, DomainEvent message) throws Exception {
        log.error("Entering Recovering mode. Sending message back to rollback queue dlq Exception: {}", e.getMessage());
        throw e;
    }

    @Override
    @Transactional
    @Retryable(
            retryFor = { Exception.class },
            maxAttempts = 2,
            backoff = @Backoff(delay = 2000)
    )
    public void handle(DomainEvent message) {
        log.info("Handling ACTIVITY_ROLLBACK message: {}", message);

        if (message instanceof ActivityRollBackEventV1 eventV1) {
            processEmployeesInBatch(eventV1.organizationId(), eventV1.amount());
        } else {
            log.error("Update Dundie Awards Message version is not supported");
        }
    }

    @Transactional
    public void processEmployeesInBatch(long organizationId, long amount) {
        int pageNumber = 0;
        Page<EmployeeModel> page;

        do {
            page = employeeService.findAllByOrganizationId(pageNumber, properties.getHandlersBatchSize(), organizationId);

            if (page.getContent().isEmpty()) {
                log.warn("No employees found on page {} for organizationId {}", pageNumber, organizationId);
                break;
            }

            log.info("Processing page {} with {} employees for organizationId {}", pageNumber, properties.getHandlersBatchSize(), organizationId);
            processEmployeeBatch(page.getContent(), amount);
            log.info("Successfully updated {} employees awards for organizationId {}", properties.getHandlersBatchSize(), organizationId);

            pageNumber++;
        } while (page.hasNext());
    }

    @Transactional
    public void processEmployeeBatch(List<EmployeeModel> employees, long amount) {

        var employeesIds = employees.stream()
                .map(EmployeeModel::getId)
                .collect(Collectors.toSet());

        employeeService.updateEmployeesAwards(employeesIds, -amount);
    }

    @Override
    public DomainEventType getSupportedType() {
        return DomainEventType.ACTIVITY_ROLLBACK;
    }
}
