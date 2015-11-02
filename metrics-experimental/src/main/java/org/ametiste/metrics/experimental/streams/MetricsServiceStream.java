package org.ametiste.metrics.experimental.streams;

import org.ametiste.metrics.MetricsService;

/**
 * <p>
 * Endpoint metrics stream. Allows to use {@link MetricsService} as endpoint for metrics.
 * </p>
 * <p>
 * <p>
 * Usable in environments where {@code MetricsService} instance already
 * exists and could be used to accept metrics.
 * </p>
 * <p>
 * <p>
 * Also may be used to adapt {@code MetricsService} to {@link MetricsStream} protocol.
 * </p>
 *
 * @since 0.2.0
 */
public class MetricsServiceStream implements MetricsStream {

    private final MetricsService metricsService;

    public MetricsServiceStream(MetricsService upstream) {
        if (upstream == null) {
            throw new IllegalArgumentException("upstream MetricsService can't be null");
        }
        this.metricsService = upstream;
    }

    @Override
    public void increment(String metricId, int value) {
        this.metricsService.increment(metricId, value);
    }

    @Override
    public void event(String metricId, int value) {
        this.metricsService.createEvent(metricId, value);
    }

}
