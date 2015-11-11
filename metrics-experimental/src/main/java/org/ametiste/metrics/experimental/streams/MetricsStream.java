package org.ametiste.metrics.experimental.streams;

/**
 * <p>
 *     Defines stream of metrics processing.
 * </p>
 *
 * @since 0.3.0
 */
public interface MetricsStream {

    void increment(String metricId, int value);

    void event(String metricId, int value);

    void gauge(String metricId, int value);

}
