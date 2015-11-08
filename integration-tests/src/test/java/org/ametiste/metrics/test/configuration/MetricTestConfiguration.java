package org.ametiste.metrics.test.configuration;

import org.ametiste.metrics.mock.MockMetricsService;
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
    @Qualifier(value = "mockService")
    public MockMetricsService metricsService() {
        return new MockMetricsService();
    }
}
