package org.ametiste.metrics.experimental.staff;

import org.ametiste.metrics.resolver.MetricsIdentifierResolver;

/**
 *
 * @since
 */
public class PrefixResolver implements MetricsIdentifierResolver {

    private final String staffPrefix;

    public PrefixResolver(String metricIdPrefix) {
        this.staffPrefix = metricIdPrefix;
    }

    @Override
    public String resolveMetricId(String metricName) {
        // TODO: how to conform to global delimiter?
        return staffPrefix + "." + metricName;
    }

}
