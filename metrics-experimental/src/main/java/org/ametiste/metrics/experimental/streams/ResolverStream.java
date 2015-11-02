package org.ametiste.metrics.experimental.streams;

import org.ametiste.metrics.resolver.MetricsIdentifierResolver;

/**
 *
 * @since
 */
public class ResolverStream implements MetricsStream {

    private final MetricsStream upstream;
    private final MetricsIdentifierResolver metricsIdentifierResolver;

    public ResolverStream(MetricsIdentifierResolver metricsIdentifierResolver, MetricsStream upstream) {
        this.upstream = upstream;
        this.metricsIdentifierResolver = metricsIdentifierResolver;
    }

    @Override
    public void increment(String metricId, int value) {
        this.upstream.increment(
            this.metricsIdentifierResolver.resolveMetricId(metricId),
            value
        );
    }

    @Override
    public void event(String metricId, int value) {
        this.upstream.event(
                this.metricsIdentifierResolver.resolveMetricId(metricId),
                value
        );
    }

    @Override
    public void gauge(String metricId, int value) {
        this.upstream.gauge(
                this.metricsIdentifierResolver.resolveMetricId(metricId),
                value
        );
    }

}
