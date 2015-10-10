package org.ametiste.metrics.experimental.staff;

import org.ametiste.metrics.boot.configuration.MetricsProperties;
import org.ametiste.metrics.experimental.activator.ResolverActivator;
import org.ametiste.metrics.experimental.activator.conditions.scopes.request.EnabledByRequestParameter;
import org.ametiste.metrics.experimental.activator.conditions.scopes.request.WithinRequestScope;
import org.ametiste.metrics.resolver.MetricsIdentifierResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @since
 */
@Configuration
@EnableConfigurationProperties(StaffPrefixResolverProperties.class)
public class StaffPrefixResolverConfiguration {

    @Autowired
    private StaffPrefixResolverProperties properties;

    @Bean
    @ConditionalOnProperty(prefix = MetricsProperties.PROPS_PREFIX, name = "staff.identifier-resolver.enabled")
    public MetricsIdentifierResolver staffPrefixResolver() {
        return new ResolverActivator(
                new PrefixResolver(properties.getStaffMetricPrefix()),
                new WithinRequestScope(),
                new EnabledByRequestParameter(properties.getStaffTriggerParameter())
        );
    }

}
