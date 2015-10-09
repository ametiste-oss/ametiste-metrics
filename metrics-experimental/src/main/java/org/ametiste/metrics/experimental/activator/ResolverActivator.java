package org.ametiste.metrics.experimental.activator;

import org.ametiste.metrics.resolver.MetricsIdentifierResolver;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @since
 */
public class ResolverActivator extends Activator implements MetricsIdentifierResolver {

    private final MetricsIdentifierResolver resolver;

    public ResolverActivator(MetricsIdentifierResolver resolver, ActivationCondition ...activationConditions) {
        this(resolver, Arrays.asList(activationConditions));
    }

    public ResolverActivator(MetricsIdentifierResolver resolver, List<ActivationCondition> activationConditions) {
        super(activationConditions);
        this.resolver = resolver;
    }

    @Override
    public String resolveMetricId(String metricName) {
        if (isActive()) {
            return resolver.resolveMetricId(metricName);
        } else {
            return metricName;
        }
    }

}
