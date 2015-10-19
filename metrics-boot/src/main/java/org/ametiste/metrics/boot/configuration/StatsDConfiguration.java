package org.ametiste.metrics.boot.configuration;

import org.ametiste.metrics.MetricsAggregator;
import org.ametiste.metrics.statsd.StatsDMetricAggregator;
import org.ametiste.metrics.statsd.client.SimpleStatsDClient;
import org.ametiste.metrics.statsd.client.StatsDClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(MetricsProperties.class)
@ConditionalOnProperty(prefix = "org.ametiste.metrics.statsd", name = "enabled", matchIfMissing = true)
public class StatsDConfiguration {

    @Autowired
    private MetricsProperties properties;

    @Bean(initMethod = "start", destroyMethod = "stop")
    public StatsDClient statsDClient() {

        return new SimpleStatsDClient(properties.getStatsd().getHost(),
                properties.getStatsd().getPort(), properties.getStatsd().getMode());
    }

    @Bean
    @CoreAggregator
    public MetricsAggregator statsDAggregator() {
        return new StatsDMetricAggregator(statsDClient());

    }

}
