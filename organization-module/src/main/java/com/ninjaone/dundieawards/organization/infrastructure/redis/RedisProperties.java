package com.ninjaone.dundieawards.organization.infrastructure.redis;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter @Setter
@Configuration
@ConfigurationProperties(prefix = "organization.cache")
public class RedisProperties {
    private long ttl;
    private long awardsSummaryTtl;
}
