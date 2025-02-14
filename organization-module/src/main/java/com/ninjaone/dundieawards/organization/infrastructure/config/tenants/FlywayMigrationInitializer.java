package com.ninjaone.dundieawards.organization.infrastructure.config.tenants;

import com.ninjaone.dundieawards.organization.infrastructure.config.tenants.models.TenantContext;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class FlywayMigrationInitializer {

    private final DataSource dataSource; // Injecting the DataSource

    public FlywayMigrationInitializer(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private List<String> getAllTenantSchemas() throws Exception {
        List<String> schemas = new ArrayList<>();
        String query = "SELECT nspname FROM pg_catalog.pg_namespace WHERE"
                + " nspname NOT LIKE 'pg_%' AND nspname != 'information_schema'";

        try (Connection connection = dataSource.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                schemas.add(rs.getString("nspname"));
            }
        }

        TenantContext.setAvailableTenants(schemas);

        return schemas;
    }

    private boolean isDatabaseAvailable(DataSource dataSource) {
        try (Connection conn = dataSource.getConnection()) {
            return conn.isValid(2); // Check if connection is valid within 2 seconds
        } catch (SQLException e) {
            return false;
        }
    }

    public void migrate() throws Exception {

        if (!isDatabaseAvailable(dataSource)) {
            log.error("Skipping Flyway migration. Database is not available");
            return;
        }

        getAllTenantSchemas().forEach(this::migrate);
    }

    public boolean migrate(String schemaName) {
        Flyway flyway = Flyway.configure()
                .dataSource(dataSource)
                .baselineOnMigrate(Boolean.TRUE)
                .failOnMissingLocations(Boolean.TRUE)
                .outOfOrder(Boolean.TRUE)
                .ignoreMigrationPatterns("*:missing")
                .schemas(schemaName)
                .locations("classpath:db/migration")
                .load();

        final var result = flyway.migrate();

        return result.success;
    }
}
