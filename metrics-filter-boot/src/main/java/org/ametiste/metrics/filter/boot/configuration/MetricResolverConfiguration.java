package org.ametiste.metrics.filter.boot.configuration;

import org.ametiste.metrics.resolver.MetricsNameResolver;
import org.ametiste.metrics.resolver.PathMetricNameResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
public class MetricResolverConfiguration {

    @Bean(name =  "timingResolver")
    public MetricsNameResolver timingResolver() {
        return new PathMetricNameResolver(Collections.emptyList(), "unsorted_timing_requests");
    }

    @Bean(name =  "countResolver")
    public MetricsNameResolver countResolver() {
        return new PathMetricNameResolver(Collections.emptyList(), "unsorted_count_requests");
    }
}
