package org.ametiste.metrics.experimental.opentsdb;

import org.ametiste.metrics.boot.configuration.CoreAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 *
 * @since
 */
@Configuration
public class OpenTSDBAggregatorConfiguration {

    @Autowired(required = false)
    private List<OpenTSDBMetricaResolveRule> resolveRules;

    @Bean
    public OpenTSDBMetricaResolver openTDSBMetricaResolver() {
        return new OpenTSDBMetricaResolver(resolveRules);
    }

    @Bean
    @CoreAggregator
    public OpenTSDBMetricsAggregator openTSDBMetricsAggregator() {
        return new OpenTSDBMetricsAggregator(openTDSBMetricaResolver());
    }

}
