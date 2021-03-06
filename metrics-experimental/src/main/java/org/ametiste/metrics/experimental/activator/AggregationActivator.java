package org.ametiste.metrics.experimental.activator;

import org.ametiste.metrics.MetricsAggregator;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @since
 */
public class AggregationActivator extends Activator implements MetricsAggregator {

    private final MetricsAggregator metricsAggregator;

    public AggregationActivator(MetricsAggregator aggregator, ActivationCondition... conditions) {
        this(aggregator, Arrays.asList(conditions));
    }

    public AggregationActivator(MetricsAggregator aggregator, List<ActivationCondition> conditions) {
        super(conditions);
        this.metricsAggregator = aggregator;
    }

    @Override
    public void gauge(String metricId, int gaugeValue) {
        invokeIfActive(() -> metricsAggregator.gauge(metricId, gaugeValue));
    }

    @Override
    public void event(String metricId, int eventValue) {
        invokeIfActive(() -> metricsAggregator.event(metricId, eventValue));
    }

    @Override
    public void increment(String metricId, int inc) {
        invokeIfActive(() -> metricsAggregator.increment(metricId, inc));
    }

}
