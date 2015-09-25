package org.ametiste.metrics.experimental;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @since
 */
public class DummyAggregatorProvider implements MetricProvider {

    private final HashMap<String, Long> metrics;

    public DummyAggregatorProvider() {
        metrics = new HashMap<>();
    }

    public void increment(String metricName) {
        increment(metricName, 1);
    }

    public void time(String metricName, long startTime, long endTime) {

        assert endTime >= startTime;

        metrics.put(metricName, endTime - startTime);
    }

    public void increment(String metricName, int inc) {
        final Long metricValue = metrics.get(metricName);
        metrics.put(metricName, metricValue + inc);
    }

    public void provideMetricValues(MetricConsumer consumer) {
        for (Map.Entry<String, Long> entry : metrics.entrySet()) {
            consumer.consume(entry.getKey(), entry.getValue().toString());
        }
    }

}
