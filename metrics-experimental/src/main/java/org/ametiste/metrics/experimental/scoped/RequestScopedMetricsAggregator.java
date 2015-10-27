package org.ametiste.metrics.experimental.scoped;

import org.ametiste.metrics.MetricsAggregator;
import org.ametiste.metrics.experimental.activator.conditions.scopes.request.RequestScopeDetector;

import java.util.HashMap;
import java.util.function.BiConsumer;

/**
 *
 * @since
 */
public class RequestScopedMetricsAggregator implements MetricsAggregator {

    private HashMap<String, Integer> requestMetrics = new HashMap<>();

    @Override
    public void gauge(String metricId, int gaugeValue) {
        putMetricValue(metricId, gaugeValue);
    }

    @Override
    public void event(String metricId, int eventValue) {
        putMetricValue(metricId, eventValue);
    }

    @Override
    public void increment(String metricId, int inc) {
        invokeIfRequestScoped(() -> requestMetrics.merge(metricId, inc, (k, v) -> v + inc));
    }

    /**
     * <p>
     * Used to consume aggregated metrics. Feeds key-value pairs of metric names and values.
     * </p>
     *
     * @param consumer metrics consumer
     */
    public void consumeMetrics(BiConsumer<String, Integer> consumer) {
        requestMetrics.forEach(consumer);
    }

    private void putMetricValue(String metricId, int value) {
        invokeIfRequestScoped(() -> requestMetrics.put(metricId, value));
    }

    private void invokeIfRequestScoped(Runnable invocation) {

        // TODO: need to inject it for unit tests
        if (RequestScopeDetector.isNotRequestScoped()) {
            return;
        }

        invocation.run();
    }

}
