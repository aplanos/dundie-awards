package com.ninjaone.dundieawards.organization.domain.specification;

import com.ninjaone.dundieawards.organization.domain.entity.Employee;
import org.springframework.data.jpa.domain.Specification;

public class EmployeeSpecification {
    public static Specification<Employee> hasOrganizationId(Long organizationId) {
        return (root, query, builder) -> {
            if (organizationId == null) {
                return builder.conjunction();
            }
            return builder.equal(root.get("organizationId"), organizationId);
        };
    }
}
