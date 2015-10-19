package org.ametiste.metrics.boot.configuration;

import org.ametiste.metrics.resolver.MetricsIdentifierResolver;
import org.ametiste.metrics.resolver.PlainMetricsIdentifierResolver;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @since 0.2.0
 */
@Configuration
public class MetricsIdentifierResolverCoreConfguration {

    @Bean
    @ConditionalOnProperty(prefix = MetricsProperties.PROPS_PREFIX,
            name = "core.identifier-resolver.enabled", matchIfMissing = true)
    @CoreIdentifierResolver
    public MetricsIdentifierResolver coreIdentifierResolver() {
        return new PlainMetricsIdentifierResolver();
    }

}
