package org.ametiste.metrics.experimental.streams;

import org.ametiste.metrics.MetricsAggregator;

/**
 *
 * @since
 */
public class AggregatorStream implements MetricsStream {

    private final MetricsAggregator metricsAggregator;

    public AggregatorStream(MetricsAggregator metricsAggregator) {
        this.metricsAggregator = metricsAggregator;
    }

    @Override
    public void increment(String metricId, int value) {
        this.metricsAggregator.increment(metricId, value);
    }

    @Override
    public void event(String metricId, int value) {
        this.metricsAggregator.event(metricId, value);
    }

    @Override
    public void gauge(String metricId, int value) {
        this.metricsAggregator.gauge(metricId, value);
    }

}
