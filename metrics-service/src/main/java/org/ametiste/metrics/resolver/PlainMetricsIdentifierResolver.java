package org.ametiste.metrics.resolver;

/**
 * Always resolves metricName to metric identifier without any modifications.
 * {@inheritDoc}
 *
 * @author ametiste
 * @since 0.1.0
 */
public class PlainMetricsIdentifierResolver implements MetricsIdentifierResolver {

    /**
     * Always resolves metricName to metric identifier without any modifications.
     * {@inheritDoc}
     */
    @Override
    public String resolveMetricId(String metricName) {
        return metricName;

    }

}
