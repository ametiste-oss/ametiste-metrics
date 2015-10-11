package org.ametiste.metrics.experimental.boot;

import org.ametiste.metrics.MetricsAggregator;
import org.ametiste.metrics.boot.configuration.CoreAggregator;
import org.ametiste.metrics.boot.configuration.MetricsCoreConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @since
 */
@Configuration
public class BootMetricServiceAggregatorConfiguration {

    @Autowired
    private CounterService counterService;

    @Bean
    @CoreAggregator
    public MetricsAggregator bootMetricServiceAggregator() {
         return new BootMetricServiceAggregator(counterService);
    }

}
