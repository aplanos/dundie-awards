package com.ninjaone.dundieawards.organization.application.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

import com.ninjaone.dundieawards.organization.application.dto.AwardSummaryStats;
import com.ninjaone.dundieawards.organization.application.dto.EmployeeModel;
import com.ninjaone.dundieawards.organization.domain.entity.Employee;
import com.ninjaone.dundieawards.organization.infrastructure.repository.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;


@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    // Test findAllByOrganizationId
    @Test
    void testFindAllByOrganizationId() {
        int page = 0;
        int pageSize = 10;
        long organizationId = 1L;

        Page<Employee> employeePage = new PageImpl<>(Collections.emptyList());
        when(employeeRepository.findAll(any(Specification.class), any(PageRequest.class)))
                .thenReturn(employeePage);

        Page<EmployeeModel> result = employeeService.findAllByOrganizationId(page, pageSize, organizationId);

        assertNotNull(result);
        verify(employeeRepository).findAll(any(Specification.class), any(PageRequest.class));
    }

    // Test findAll without specification
    @Test
    void testFindAll() {
        int page = 0;
        int pageSize = 10;

        Page<Employee> employeePage = new PageImpl<>(Collections.emptyList());
        when(employeeRepository.findAll(any(PageRequest.class)))
                .thenReturn(employeePage);

        Page<EmployeeModel> result = employeeService.findAll(page, pageSize);

        assertNotNull(result);
        verify(employeeRepository).findAll(any(PageRequest.class));
    }

    // Test findById
    @Test
    void testFindById() {
        long id = 1L;
        Employee employee = new Employee();
        when(employeeRepository.findById(id)).thenReturn(Optional.of(employee));

        EmployeeModel result = employeeService.findById(id);

        assertNotNull(result);
        verify(employeeRepository).findById(id);
    }

    // Test findById throws EntityNotFoundException
    @Test
    void testFindByIdThrowsEntityNotFoundException() {
        long id = 1L;
        when(employeeRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> employeeService.findById(id));
    }

    // Test save (currently not implemented)
    @Test
    void testSave() {
        EmployeeModel employeeModel = new EmployeeModel();
        EmployeeModel result = employeeService.save(employeeModel);

        assertNull(result); // Since the save method is not implemented
    }

    // Test delete
    @Test
    void testDelete() {
        long id = 1L;
        Employee employee = new Employee();
        when(employeeRepository.findById(id)).thenReturn(Optional.of(employee));

        employeeService.delete(id);

        verify(employeeRepository).save(employee);
    }

    // Test delete throws EntityNotFoundException
    @Test
    void testDeleteThrowsEntityNotFoundException() {
        long id = 1L;
        when(employeeRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> employeeService.delete(id));
    }

    // Test updateEmployeesAwards
    @Test
    void testUpdateEmployeesAwards() {
        Set<Long> employeeIds = new HashSet<>(Arrays.asList(1L, 2L));
        long amount = 100;

        doNothing().when(employeeRepository).updateEmployeesAwards(employeeIds, amount);

        employeeService.updateEmployeesAwards(employeeIds, amount);

        verify(employeeRepository).updateEmployeesAwards(employeeIds, amount);
    }

    // Test getTotalEmployeesAwards
    @Test
    void testGetTotalEmployeesAwards() {
        AwardSummaryStats awardSummaryStats = new AwardSummaryStats();
        when(employeeRepository.getAwardSummaryStats()).thenReturn(() -> 100L);

        AwardSummaryStats result = employeeService.getTotalEmployeesAwards();

        assertNotNull(result);
        verify(employeeRepository).getAwardSummaryStats();
    }
}
