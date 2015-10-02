package org.ametiste.metrics.filter.boot.configuration;

import org.ametiste.metrics.boot.configuration.MetricsServiceConfiguration;
import org.ametiste.metrics.MetricsService;
import org.ametiste.metrics.filter.RequestCountFilter;
import org.ametiste.metrics.resolver.MetricsIdentifierResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;

@Configuration
@Import({MetricsServiceConfiguration.class, MetricResolverConfiguration.class})
public class MetricFilterConfiguration {

    @Autowired
    private MetricsService metricsService;

    @Autowired
    private MetricsIdentifierResolver timingResolver;

    @Autowired
    private MetricsIdentifierResolver countResolver;

    @Bean
    public RequestCountFilter metricTimingFilter() {

        return new RequestCountFilter(metricsService, timingResolver);
    }

    @Bean
    public RequestCountFilter metricCountFilter() {

        return new RequestCountFilter(metricsService, countResolver);
    }


}
