package com.ninjaone.dundieawards.organization.domain.specification;

import org.springframework.data.jpa.domain.Specification;

public class TenantSpecification {
    public static <T> Specification<T> hasOrganizationId(Long organizationId) {
        return (root, query, builder) -> {
            if (organizationId == null) {
                return builder.conjunction();
            }
            return builder.equal(root.get("organizationId"), organizationId);
        };
    }
}
