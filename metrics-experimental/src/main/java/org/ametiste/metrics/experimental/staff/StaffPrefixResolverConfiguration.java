package org.ametiste.metrics.experimental.staff;

import com.sun.org.apache.xpath.internal.operations.Variable;
import org.ametiste.metrics.experimental.activator.ResolverActivator;
import org.ametiste.metrics.experimental.activator.conditions.scopes.request.EnabledByRequestParameter;
import org.ametiste.metrics.experimental.activator.conditions.scopes.request.WithinRequestScope;
import org.ametiste.metrics.experimental.resolver.templated.VariableBind;
import org.ametiste.metrics.resolver.MetricsIdentifierResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @since
 */
@Configuration
// TODO : I guess we need MetricsIdentifierResolver usage refactoring,
// there is no way to inject my own resolver
public class StaffPrefixResolverConfiguration {

    @Autowired
    private StaffPrefixResolverProperties properties;

    @Bean
    public MetricsIdentifierResolver staffPrefixResolver() {
        return new ResolverActivator(
                new PrefixResolver(properties.getStaffMetricPrefix()),
                new WithinRequestScope(),
                new EnabledByRequestParameter(properties.getStaffTriggerParameter())
        );
    }

}
