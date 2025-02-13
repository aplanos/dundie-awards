package com.ninjaone.dundieawards.organization.infraestructure.config;

import com.ninjaone.dundieawards.organization.infraestructure.config.tenants.FlywayMigrationInitializer;
import com.ninjaone.dundieawards.organization.infraestructure.config.tenants.MultiTenantSchemaConnectionProvider;
import com.ninjaone.dundieawards.organization.infraestructure.config.tenants.SchemaTenantIdentifierResolver;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.SpringSessionContext;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.ninjaone.dundieawards.organization.infraestructure.adapter.repository",
        entityManagerFactoryRef = "organizationEntityManagerFactory",
        transactionManagerRef= "organizationTransactionManager"
)
public class DataSourceConfig {

    @Bean
    @ConfigurationProperties("spring.datasource")
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("local.datasource.configuration")
    public DataSource dataSource() {
        return dataSourceProperties().initializeDataSourceBuilder()
                .type(HikariDataSource.class).build();
    }

    @Bean(initMethod = "migrate")
    public FlywayMigrationInitializer flywayMigrationInitializer() {
        return new FlywayMigrationInitializer(dataSource());
    }

    @Bean
    @DependsOn(value = "flywayMigrationInitializer")
    public LocalContainerEntityManagerFactoryBean organizationEntityManagerFactory() throws SQLException {

        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan("com.ninjaone.dundieawards.organization.domain.entity");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        em.setJpaPropertyMap(jpaVendorProperties());

        return em;
    }

    @Bean
    public MultiTenantConnectionProvider<String> multiTenantConnectionProvider() {
        return new MultiTenantSchemaConnectionProvider(dataSource());
    }

    @Bean
    public CurrentTenantIdentifierResolver<String> tenantIdentifierResolver() {
        return new SchemaTenantIdentifierResolver();
    }

    @Bean
    public JpaTransactionManager organizationTransactionManager() throws SQLException {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(organizationEntityManagerFactory().getObject());
        return transactionManager;
    }

    private Map<String, Object> jpaVendorProperties() {
        Map<String, Object> properties = new HashMap<>();

        // Configure Hibernate properties
        properties.put(AvailableSettings.DIALECT, "org.hibernate.dialect.PostgreSQLDialect");
        properties.put(AvailableSettings.USE_QUERY_CACHE, false);
        properties.put(AvailableSettings.USE_SECOND_LEVEL_CACHE, false);
        properties.put(AvailableSettings.QUERY_PLAN_CACHE_MAX_SIZE, 600);

        properties.put(AvailableSettings.USE_SQL_COMMENTS, false);
        properties.put(AvailableSettings.GENERATE_STATISTICS, false);
        properties.put(AvailableSettings.CURRENT_SESSION_CONTEXT_CLASS, SpringSessionContext.class.getName());

        // Optional for debug queries in local mode
        // properties.put(AvailableSettings.FORMAT_SQL, true);
        // properties.put(AvailableSettings.SHOW_SQL, true);

        properties.put(AvailableSettings.MULTI_TENANT_CONNECTION_PROVIDER, multiTenantConnectionProvider());
        properties.put(AvailableSettings.MULTI_TENANT_IDENTIFIER_RESOLVER, tenantIdentifierResolver());
        properties.put(AvailableSettings.ALLOW_UPDATE_OUTSIDE_TRANSACTION, false);

        return properties;
    }
}
