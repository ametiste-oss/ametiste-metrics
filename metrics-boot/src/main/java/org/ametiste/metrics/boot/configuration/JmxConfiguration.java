package org.ametiste.metrics.boot.configuration;

import org.ametiste.metrics.jmx.JmxMetricAggregator;
import org.ametiste.metrics.MetricsAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(MetricsProperties.class)
//@ConditionalOnProperty (value = "com.dph.metrics.yammer.disabled", havingValue = "true", matchIfMissing = false)
public class JmxConfiguration {

    @Autowired
    private MetricsProperties properties;
    @Bean
    public MetricsAggregator jmxAggregator() {

        return new JmxMetricAggregator(properties.getJmx().getDomain());
    }

}
