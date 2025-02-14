package com.ninjaone.dundieawards.organization.infrastructure.rest;

import java.util.HashMap;
import java.util.Map;

import com.ninjaone.dundieawards.organization.application.api.EmployeeApi;
import com.ninjaone.dundieawards.organization.application.dto.AwardSummaryStats;
import com.ninjaone.dundieawards.organization.application.dto.EmployeeModel;
import com.ninjaone.dundieawards.organization.application.service.EmployeeService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
public class EmployeeController implements EmployeeApi {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('employee_manage')")
    public PagedModel<EmployeeModel> getAllEmployees(@RequestParam(value = "page") @Min(0) int page,
                                                     @RequestParam(value = "pageSize", required = false, defaultValue = "10") @Min(1) @Max(100) int pageSize) {
        return new PagedModel<>(employeeService.findAll(page, pageSize));
    }

    @GetMapping("/awards-summary-stats")
    @PreAuthorize("hasAuthority('employee_manage')")
    public AwardSummaryStats getAwardSummaryStats() {
        return employeeService.getTotalEmployeesAwards();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('employee_manage')")
    public EmployeeModel createEmployee(@RequestBody EmployeeModel employee) {
        return employeeService.save(employee);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('employee_manage')")
    public ResponseEntity<EmployeeModel> getEmployeeById(@PathVariable @Positive Long id) {
        return ResponseEntity.ok(employeeService.findById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('employee_manage')")
    public ResponseEntity<EmployeeModel> updateEmployee(
            @PathVariable @Positive Long id,
            @RequestBody @Valid EmployeeModel employeeDetails
    ) {
        final var employee = employeeService.findById(id);

        employee.setFirstName(employeeDetails.getFirstName());
        employee.setLastName(employeeDetails.getLastName());

        final var updatedEmployee = employeeService.save(employee);
        return ResponseEntity.ok(updatedEmployee);
    }

    @DeleteMapping("/employees/{id}")
    @PreAuthorize("hasAuthority('employee_manage')")
    public ResponseEntity<?> deleteEmployee(@PathVariable @Positive Long id) {
        employeeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}