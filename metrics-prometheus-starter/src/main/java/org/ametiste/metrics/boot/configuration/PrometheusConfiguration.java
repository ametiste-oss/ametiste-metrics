package org.ametiste.metrics.boot.configuration;

import org.ametiste.metrics.MetricsAggregator;
import org.ametiste.metrics.prometheus.PrometheusMetricAggregator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "org.ametiste.metrics.prometheus", name = "enabled", matchIfMissing = true)
public class PrometheusConfiguration {

    @Bean
    @CoreAggregator
    public MetricsAggregator prometheusAggregator() {
        return new PrometheusMetricAggregator();
    }
}
