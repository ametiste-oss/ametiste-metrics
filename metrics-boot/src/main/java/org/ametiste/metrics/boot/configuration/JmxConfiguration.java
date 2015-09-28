package org.ametiste.metrics.boot.configuration;

import org.ametiste.metrics.jmx.JmxMetricAggregator;
import org.ametiste.metrics.MetricsAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(MetricsProperties.class)
@ConditionalOnProperty(prefix="org.ametiste.metrics.yammer", name = "enabled", matchIfMissing = true)
public class JmxConfiguration {

    @Autowired
    private MetricsProperties properties;
    @Bean
    public MetricsAggregator jmxAggregator() {

        return new JmxMetricAggregator(properties.getJmx().getDomain());
    }

}
