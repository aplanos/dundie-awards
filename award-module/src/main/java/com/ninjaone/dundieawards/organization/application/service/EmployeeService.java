package com.ninjaone.dundieawards.organization.application.service;

import com.ninjaone.dundieawards.organization.application.dto.AwardSummaryStats;
import com.ninjaone.dundieawards.organization.application.dto.EmployeeModel;
import org.springframework.data.domain.Page;

import java.util.Set;

public interface EmployeeService {

    Page<EmployeeModel> findAllByOrganizationId(int page, int pageSize, long organizationId);
    Page<EmployeeModel> findAll(int page, int pageSize);
    EmployeeModel save(EmployeeModel employee);
    EmployeeModel findById(long id);
    void delete(long id);
    void updateEmployeesAwards(Set<Long> employeeIds, long amount);
    AwardSummaryStats getTotalEmployeesAwards();
}
