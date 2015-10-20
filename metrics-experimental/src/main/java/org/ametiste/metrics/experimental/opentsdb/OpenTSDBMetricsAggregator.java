package org.ametiste.metrics.experimental.opentsdb;

import org.ametiste.metrics.MetricsAggregator;

/**
 *
 * @since
 */
public class OpenTSDBMetricsAggregator implements MetricsAggregator {

    private final OpenTSDBMetricaResolver resolver;

    public OpenTSDBMetricsAggregator(OpenTSDBMetricaResolver resolver) {
        this.resolver = resolver;
    }

    @Override
    public void increment(String metricId) {
        System.out.println(String.format("opentsdb metric -> %s [%s]",
                resolver.resolveMetrica(metricId).id, resolver.resolveMetrica(metricId).tags));
    }

    @Override
    public void event(String metricId, int evenValue) {
        System.out.println(String.format("opentsdb metric -> %s [%s]",
                resolver.resolveMetrica(metricId).id, resolver.resolveMetrica(metricId).tags));
    }

    @Override
    public void increment(String metricId, int inc) {
        System.out.println(String.format("opentsdb metric -> %s [%s]",
                resolver.resolveMetrica(metricId).id, resolver.resolveMetrica(metricId).tags));
    }

}
