package com.ninjaone.dundieawards.organization.infraestructure.adapter.persistence;

import com.ninjaone.dundieawards.organization.domain.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee> {

    @Modifying
    @Transactional
    @Query("UPDATE Employee e SET e.dundieAwards = e.dundieAwards + :amount WHERE e.id IN :employeeIds")
    void updateEmployeesAwards(Set<Long> employeeIds, long amount);
}
