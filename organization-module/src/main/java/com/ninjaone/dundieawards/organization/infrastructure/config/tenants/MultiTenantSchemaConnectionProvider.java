package com.ninjaone.dundieawards.organization.infrastructure.config.tenants;

import org.hibernate.HibernateException;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class MultiTenantSchemaConnectionProvider implements MultiTenantConnectionProvider<String> {

    private final DataSource dataSource;

    public MultiTenantSchemaConnectionProvider(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Connection getAnyConnection() throws SQLException {
        return dataSource.getConnection();
    }

    @Override
    public void releaseAnyConnection(Connection connection) throws SQLException {
        connection.close();
    }

    @Override
    public Connection getConnection(String tenantIdentifier) throws SQLException {

        // Validate tenant identifier to avoid SQL injection
        if (!isValidSchema(tenantIdentifier)) {
            throw new HibernateException("Invalid tenant identifier: " + tenantIdentifier);
        }

        final Connection connection = getAnyConnection();
        try (Statement stmt = connection.createStatement()) {
            // Switch schema safely using the tenantIdentifier
            stmt.execute("SET search_path to " + tenantIdentifier);
        } catch (SQLException e) {
            throw new HibernateException(
                    "Could not alter JDBC connection to specified schema [" + tenantIdentifier + "]", e
            );
        }
        return connection;
    }

    @Override
    public void releaseConnection(String tenantIdentifier, Connection connection) throws SQLException {
        try (Statement stmt = connection.createStatement()){
            stmt.execute("SET search_path to public");
        } catch (SQLException e) {
            throw new HibernateException(
                    "Could not alter JDBC connection to specified schema [" + tenantIdentifier + "]", e
            );

        }
        connection.close();
    }

    @Override
    public boolean supportsAggressiveRelease() {
        return true;
    }

    @Override
    public boolean isUnwrappableAs(Class aClass) {
        return false;
    }

    @Override
    public <T> T unwrap(Class<T> aClass) {
        return null;
    }

    private boolean isValidSchema(String tenantIdentifier) {
        // Allow only alphanumeric characters and underscores in schema names
        return tenantIdentifier != null && tenantIdentifier.matches("[a-zA-Z0-9_]+");
    }
}
