package org.ametiste.metrics.experimental.streams;

import org.ametiste.metrics.MetricsService;

/**
 *
 * @since
 */
public class MetricsStreamService implements MetricsService {

    private MetricsStream metricsStreams;

    public MetricsStreamService(MetricsStream metricsStreams) {
        this.metricsStreams = metricsStreams;
    }

    @Override
    public void increment(String metricId, int incrementValue) {
        this.metricsStreams.increment(metricId, incrementValue);
    }

    @Override
    public void gauge(String metricId, int gaugeValue) {
        //TODO implement it
        throw new UnsupportedOperationException("Not implemented");
    }


    @Override
    public void createEvent(String metricId, int eventValue) {
        this.metricsStreams.event(metricId, eventValue);
    }

}
