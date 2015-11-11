package org.ametiste.metrics.experimental.streams;

import org.ametiste.metrics.MetricsService;

/**
 *
 * @since
 */
public class MetricsStreamService implements MetricsService {

    private MetricsStream metricsStream;

    public MetricsStreamService(MetricsStream metricsStream) {
        this.metricsStream = metricsStream;
    }

    @Override
    public void increment(String metricId, int incrementValue) {
        this.metricsStream.increment(metricId, incrementValue);
    }

    @Override
    public void gauge(String metricId, int gaugeValue) {
        this.metricsStream.gauge(metricId, gaugeValue);
    }

    @Override
    public void createEvent(String metricId, int eventValue) {
        this.metricsStream.event(metricId, eventValue);
    }

}
