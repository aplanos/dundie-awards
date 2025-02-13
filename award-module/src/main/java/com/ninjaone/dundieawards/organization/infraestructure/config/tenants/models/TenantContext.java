package com.ninjaone.dundieawards.organization.infraestructure.config.tenants.models;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class TenantContext {

    public static final String DEFAULT_TENANT = "public";

    private static final ThreadLocal<String> currentTenant = new ThreadLocal<>();
    private static final Set<String> availableTenants = Collections.synchronizedSet(new HashSet<>());

    /**
     * Sets the current tenant for the current thread.
     * If the tenant is not in the available tenants list, the default tenant is set.
     *
     * @param tenant the tenant to set
     */
    public static void setCurrentTenant(String tenant) {
        if (isTenantAvailable(tenant)) {
            currentTenant.set(tenant);
        } else {
            currentTenant.set(DEFAULT_TENANT);
        }
    }

    /**
     * Retrieves the current tenant for the current thread.
     * If no tenant is set, the default tenant is returned.
     *
     * @return the current tenant or the default tenant if none is set
     */
    public static String getCurrentTenant() {
        return Optional.ofNullable(currentTenant.get()).orElse(DEFAULT_TENANT);
    }

    /**
     * Clears the current tenant from the thread-local context.
     */
    public static void clear() {
        currentTenant.remove();
    }

    /**
     * Sets the list of available tenants.
     *
     * @param tenants list of tenant schemas
     */
    public static void setAvailableTenants(List<String> tenants) {
        availableTenants.addAll(tenants);
    }

    /**
     * Adds a single tenant to the available tenants list.
     *
     * @param tenant the tenant schema to add
     */
    public static void addAvailableTenant(String tenant) {
        availableTenants.add(tenant);
    }

    /**
     * Checks if a tenant is available.
     *
     * @param tenant the tenant schema to check
     * @return true if the tenant is available, false otherwise
     */
    public static boolean isTenantAvailable(String tenant) {
        return availableTenants.contains(tenant);
    }
}
