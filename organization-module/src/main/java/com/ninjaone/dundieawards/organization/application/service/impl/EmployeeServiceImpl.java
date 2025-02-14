package com.ninjaone.dundieawards.organization.application.service.impl;

import com.ninjaone.dundieawards.common.infrastructure.utils.MapperUtils;
import com.ninjaone.dundieawards.common.domain.specification.TenantSpecification;
import com.ninjaone.dundieawards.organization.application.dto.AwardSummaryStats;
import com.ninjaone.dundieawards.organization.application.dto.EmployeeModel;
import com.ninjaone.dundieawards.organization.application.service.EmployeeService;
import com.ninjaone.dundieawards.organization.domain.entity.Employee;
import com.ninjaone.dundieawards.organization.infrastructure.repository.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final ModelMapper modelMapper = MapperUtils.getDefaultMapper();
    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Page<EmployeeModel> findAllByOrganizationId(int page, int pageSize, long organizationId) {
        return findAll(page, pageSize,
                Arrays.asList("organizationId", "firstName"),
                TenantSpecification.hasTenantId("organizationId", organizationId)
        );
    }

    @Override
    public Page<EmployeeModel> findAll(int page, int pageSize) {
        return findAll(page, pageSize, Collections.singletonList("id"), null);
    }

    private Page<EmployeeModel> findAll(int page, int pageSize, List<String> sortKeys, Specification<Employee> specification) {
        var pageRequest = PageRequest.of(page, pageSize,
                Sort.by(Sort.Direction.ASC, sortKeys.toArray(new String[0]))
        );

        final var employees = specification == null
                ? employeeRepository.findAll(pageRequest)
                : employeeRepository.findAll(specification, pageRequest);

        var models =  employees.getContent()
                .stream()
                .map(e -> modelMapper.map(e, EmployeeModel.class))
                .toList();

        return new PageImpl<>(models, pageRequest, employees.getTotalElements());
    }

    @Override
    public EmployeeModel save(EmployeeModel employee) {
        return null;
    }

    @Override
    public EmployeeModel findById(long id) {
        final var employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Employee with id %s not found", id)
                ));

        return modelMapper.map(employee, EmployeeModel.class);
    }

    @Override
    @CacheEvict(value = "awards-summary-stats", allEntries = true)
    public void delete(long id) {
        final var employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Employee with id %s not found", id)
                ));

        employee.setDeleted(true);

        employeeRepository.save(employee);
    }

    @Override
    @Transactional
    @CacheEvict(value = "awards-summary-stats", allEntries = true)
    public void updateEmployeesAwards(Set<Long> employeeIds, long amount) {
        employeeRepository.updateEmployeesAwards(employeeIds, amount);
    }

    @Override
    @Cacheable(value = "awards-summary-stats")
    public AwardSummaryStats getTotalEmployeesAwards() {
        final var stats = employeeRepository.getAwardSummaryStats();
        return MapperUtils.mapTo(stats, AwardSummaryStats.class);
    }
}
