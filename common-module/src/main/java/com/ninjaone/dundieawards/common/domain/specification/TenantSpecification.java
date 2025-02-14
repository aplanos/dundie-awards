package com.ninjaone.dundieawards.common.domain.specification;

import org.springframework.data.jpa.domain.Specification;

public class TenantSpecification {
    public static <T> Specification<T> hasTenantId(String tenantKey, Long tenantId) {
        return (root, query, builder) -> {
            if (tenantId == null) {
                return builder.conjunction();
            }
            return builder.equal(root.get(tenantKey), tenantId);
        };
    }
}
