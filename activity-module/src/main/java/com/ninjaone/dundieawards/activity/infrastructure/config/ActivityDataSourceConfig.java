package com.ninjaone.dundieawards.activity.infrastructure.config;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.cfg.AvailableSettings;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
@EnableJpaRepositories(basePackages = "com.ninjaone.dundieawards.activity.infrastructure.repository",
        entityManagerFactoryRef = "activityEntityManagerFactory",
        transactionManagerRef= "activityTransactionManager"
)
@Qualifier("activityDatasourceConfig")
public class ActivityDataSourceConfig {

    @Bean
    @ConfigurationProperties("spring.datasource")
    @Qualifier("activityDataSourceProperties")
    public DataSourceProperties activityDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("local.datasource.configuration")
    @Qualifier("activityDataSource")
    public DataSource activityDataSource() {
        return activityDataSourceProperties().initializeDataSourceBuilder()
                .type(HikariDataSource.class).build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean activityEntityManagerFactory() throws SQLException {

        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(activityDataSource());
        em.setPackagesToScan("com.ninjaone.dundieawards.activity.domain.entity");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        em.setJpaPropertyMap(jpaVendorProperties());

        return em;
    }

    @Bean
    public JpaTransactionManager activityTransactionManager() throws SQLException {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(activityEntityManagerFactory().getObject());
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

        properties.put(AvailableSettings.ALLOW_UPDATE_OUTSIDE_TRANSACTION, false);

        return properties;
    }
}
