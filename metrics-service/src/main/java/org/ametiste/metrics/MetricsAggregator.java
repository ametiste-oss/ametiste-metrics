package org.ametiste.metrics;

/**
 * Single endpoint of metrics aggregation which goals and work principles highly depend on implementation
 *
 * @author ametiste
 * @since 0.1.0
 */
public interface MetricsAggregator {

    void increment(String metricId);

    void event(String metricId, int evenValue);

    void increment(String metricId, int inc);
}
