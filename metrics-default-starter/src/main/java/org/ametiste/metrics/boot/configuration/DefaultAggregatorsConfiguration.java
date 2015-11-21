package org.ametiste.metrics.boot.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * <p>
 * Defines default configured aggregators. See concrete aggregators configurations for details.
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
public class DefaultAggregatorsConfiguration {

}
