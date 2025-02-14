package com.ninjaone.dundieawards.organization.application.service.messaging;

import com.ninjaone.dundieawards.organization.application.dto.EmployeeModel;
import com.ninjaone.dundieawards.organization.application.service.EmployeeService;
import com.ninjaone.dundieawards.organization.domain.event.DomainEvent;
import com.ninjaone.dundieawards.organization.domain.event.DomainEventType;
import com.ninjaone.dundieawards.organization.domain.event.increase_dundie_awards.IncreaseDundieAwardsEventV1;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UpdateDundieAwardsMessageHandler implements MessageHandler {

    @Value("${employee.pageSize:1}")
    private int pageSize;

    private final EmployeeService employeeService;

    public UpdateDundieAwardsMessageHandler(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    @Transactional
    public void handle(DomainEvent message) {
        log.info("Handling INCREASE_DUNDIE_AWARDS message: {}", message);

        if (message instanceof IncreaseDundieAwardsEventV1 eventV1) {
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
            page = employeeService.findAllByOrganizationId(pageNumber, pageSize, organizationId);

            if (page.getContent().isEmpty()) {
                log.warn("No employees found on page {} for organizationId {}", pageNumber, organizationId);
                break;
            }

            log.info("Processing page {} with {} employees for organizationId {}", pageNumber, pageSize, organizationId);
            processEmployeeBatch(page.getContent(), amount);
            log.info("Successfully updated {} employees awards for organizationId {}", pageSize, organizationId);

            pageNumber++;
        } while (page.hasNext());
    }

    @Transactional
    public void processEmployeeBatch(List<EmployeeModel> employees, long amount) {

        var employeesIds = employees.stream()
                .map(EmployeeModel::getId)
                .collect(Collectors.toSet());

        employeeService.updateEmployeesAwards(employeesIds, amount);
    }

    @Override
    public DomainEventType getSupportedType() {
        return DomainEventType.INCREASE_DUNDIE_AWARDS;
    }
}
