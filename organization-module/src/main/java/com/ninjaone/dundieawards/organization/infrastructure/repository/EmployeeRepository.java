package com.ninjaone.dundieawards.organization.infrastructure.repository;

import com.ninjaone.dundieawards.organization.domain.entity.Employee;
import com.ninjaone.dundieawards.organization.infrastructure.repository.projection.AwardSummaryStatsProjection;
import com.ninjaone.dundieawards.organization.infrastructure.repository.projection.EmployeeListItemProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee> {

    @Query("""
        SELECT e.id AS id, e.firstName AS firstName, e.lastName AS lastName, 
               e.dundieAwards AS dundieAwards, o.id AS organizationId, o.name AS organizationName
        FROM Employee e
        JOIN Organization o ON e.organizationId = o.id
    """)
    Page<EmployeeListItemProjection> findAllWithOrganization(Pageable pageable);

    @Query("""
        SELECT e.id AS id, e.firstName AS firstName, e.lastName AS lastName, 
               e.dundieAwards AS dundieAwards, o.id AS organizationId, o.name AS organizationName
        FROM Employee e
        JOIN Organization o ON e.organizationId = o.id
    """)
    Page<EmployeeListItemProjection> findAllWithOrganization(@Param("spec") Specification<Employee> spec, Pageable pageable);

    @Modifying
    @Transactional
    @Query("UPDATE Employee e SET e.dundieAwards = e.dundieAwards + :amount WHERE e.id IN :employeeIds")
    void updateEmployeesAwards(Set<Long> employeeIds, long amount);

    @Query("SELECT SUM(e.dundieAwards) as totalDundieAwards FROM Employee e")
    AwardSummaryStatsProjection getAwardSummaryStats();
}
