package org.ametiste.metrics.boot.configuration;

import org.ametiste.metrics.MetricsAggregator;
import org.ametiste.metrics.NullMetricsAggregator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * <p>
 * Defines default configured aggreagtors. See concrete aggregators configurations for details.
 * </p>
 *
 * @see JmxConfiguration
 * @see StatsDConfiguration
 * @since 0.2.0
 */
@Import({
        JmxConfiguration.class,
        StatsDConfiguration.class
})
@Configuration
public class DefaultAggreagtorsConfiguration {

    @Bean
    @CoreAggregator
    public MetricsAggregator nullAggregator() {
        return new NullMetricsAggregator();
    }

}
