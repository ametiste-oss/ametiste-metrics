package org.ametiste.metrics.boot.configuration;

import org.ametiste.metrics.MetricsAggregator;
import org.ametiste.metrics.NullMetricsAggregator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Defines empty configured aggregator if any else are missing. Provides successful test launch
 * @since 0.2.0
 */
@Configuration
public class EmptyAggregatorsConfiguration {

    @Bean
    @CoreAggregator
    @ConditionalOnMissingBean(MetricsAggregator.class)
    public MetricsAggregator nullAggregator() {
        return new NullMetricsAggregator();
    }

}
