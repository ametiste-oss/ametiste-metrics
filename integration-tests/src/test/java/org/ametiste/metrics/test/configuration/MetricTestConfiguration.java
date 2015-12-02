package org.ametiste.metrics.test.configuration;

import org.ametiste.metrics.MetricsAggregator;
import org.ametiste.metrics.boot.configuration.CoreAggregator;
import org.ametiste.metrics.mock.aggregator.MockMetricsAggregator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by atlantis on 11/7/15.
 */
@Configuration
public class MetricTestConfiguration {

    @Bean
    public Service service() {
        return new Service();
    }

    @Bean
    @CoreAggregator
    @Qualifier("first")
    public MetricsAggregator firstAggregator() {
        return new MockMetricsAggregator();
    }

    @Bean
    @Qualifier("second")
    public MetricsAggregator secondAggregator() {
        return new MockMetricsAggregator();
    }
}
