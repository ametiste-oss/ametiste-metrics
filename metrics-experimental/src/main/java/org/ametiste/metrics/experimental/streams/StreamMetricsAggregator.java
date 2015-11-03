package org.ametiste.metrics.experimental.streams;

import org.ametiste.metrics.MetricsAggregator;

import java.util.List;

/**
 *
 * @since
 */
public class StreamMetricsAggregator implements MetricsAggregator {

    private final List<MetricsStream> metricsStream;

    public StreamMetricsAggregator(List<MetricsStream> metricsStream) {
        this.metricsStream = metricsStream;
    }

    @Override
    public void gauge(String metricId, int gaugeValue) {
        metricsStream.forEach((s) ->
                s.gauge(metricId, gaugeValue)
        );
    }

    @Override
    public void event(String metricId, int eventValue) {
        this.metricsStream.forEach((s) ->
                s.event(metricId, eventValue)
        );
    }

    @Override
    public void increment(String metricId, int inc) {
        this.metricsStream.forEach((s) ->
                s.increment(metricId, inc)
        );
    }
}
