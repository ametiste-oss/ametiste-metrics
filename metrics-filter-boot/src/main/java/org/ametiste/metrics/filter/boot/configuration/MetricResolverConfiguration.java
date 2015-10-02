package org.ametiste.metrics.filter.boot.configuration;

import org.ametiste.metrics.resolver.MetricsIdentifierResolver;
import org.ametiste.metrics.resolver.PathMetricsIdentifierResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
public class MetricResolverConfiguration {

    @Bean(name =  "timingResolver")
    public MetricsIdentifierResolver timingResolver() {
        return new PathMetricsIdentifierResolver(Collections.emptyList(), "unsorted_timing_requests");
    }

    @Bean(name =  "countResolver")
    public MetricsIdentifierResolver countResolver() {
        return new PathMetricsIdentifierResolver(Collections.emptyList(), "unsorted_count_requests");
    }
}
