package com.ninjaone.dundieawards.organization.infraestructure.adapter.rest;

import java.util.HashMap;
import java.util.Map;

import com.ninjaone.dundieawards.organization.application.dto.AwardSummaryStats;
import com.ninjaone.dundieawards.organization.application.dto.EmployeeModel;
import com.ninjaone.dundieawards.organization.application.service.EmployeeService;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employees/v1")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('employee_manage')")
    public PagedModel<EmployeeModel> getAllEmployees(@RequestParam(value = "page") int page,
                                                     @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {
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
    public ResponseEntity<EmployeeModel> getEmployeeById(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.findById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('employee_manage')")
    public ResponseEntity<EmployeeModel> updateEmployee(
            @PathVariable Long id,
            @RequestBody EmployeeModel employeeDetails
    ) {
        final var employee = employeeService.findById(id);

        employee.setFirstName(employeeDetails.getFirstName());
        employee.setLastName(employeeDetails.getLastName());

        final var updatedEmployee = employeeService.save(employee);
        return ResponseEntity.ok(updatedEmployee);
    }

    @DeleteMapping("/employees/{id}")
    @PreAuthorize("hasAuthority('employee_manage')")
    public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Long id) {
        employeeService.delete(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}