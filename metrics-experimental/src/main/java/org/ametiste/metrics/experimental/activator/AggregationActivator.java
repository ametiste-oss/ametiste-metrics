package org.ametiste.metrics.experimental.activator;

import org.ametiste.metrics.MetricsAggregator;

import java.lang.Boolean;import java.lang.Override;import java.lang.String;import java.util.Arrays;
import java.util.List;

/**
 *
 * @since
 */
public class AggregationActivator implements MetricsAggregator {

    private final MetricsAggregator metricsAggregator;

    private final List<ActivationCondition> activationConditions;

    public AggregationActivator(MetricsAggregator aggregator, ActivationCondition... conditions) {
        this(aggregator, Arrays.asList(conditions));
    }

    public AggregationActivator(MetricsAggregator aggregator, List<ActivationCondition> conditions) {
        this.metricsAggregator = aggregator;
        this.activationConditions = conditions;
    }

    @Override
    public void increment(String metricId) {
        if (isActivated()) {
            metricsAggregator.increment(metricId);
        }
    }

    @Override
    public void event(String metricId, int evenValue) {
        if (isActivated()) {
            metricsAggregator.event(metricId, evenValue);
        }
    }

    @Override
    public void increment(String metricId, int inc) {
        if (isActivated()) {
            metricsAggregator.increment(metricId, inc);
        }
    }

    private boolean isActivated() {
        return activationConditions
                .stream()
                .map(ActivationCondition::checkActivation)
                .allMatch(Boolean.TRUE::equals);
    }

}
