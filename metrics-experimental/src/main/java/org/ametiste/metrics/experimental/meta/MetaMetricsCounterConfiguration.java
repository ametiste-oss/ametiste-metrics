package org.ametiste.metrics.experimental.meta;

import org.ametiste.metrics.MetricsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @since
 */
@Configuration
// TODO: I can't find the way to configure it :[
public class MetaMetricsCounterConfiguration {

    @Autowired
    private MetricsService metricsService;

    @Bean
    public MetricsService metricsService() {
        return new MetaMetricsCounter(metricsService);
    }

}
