package org.ametiste.metrics;

/**
 * Single endpoint of metrics aggregation which goals and work principles highly depend on implementation
 *
 * @since 0.1.0
 * @author ametiste
 */
public interface MetricsAggregator {

	void increment(String metricName);

	void event(String metricName, int evenValue);

    void increment(String metricName, int inc);
}
