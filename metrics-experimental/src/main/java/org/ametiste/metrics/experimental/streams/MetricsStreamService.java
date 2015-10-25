package org.ametiste.metrics.experimental.streams;

import org.ametiste.metrics.MetricsService;
import org.ametiste.metrics.NullMetricsAggregator;
import org.ametiste.metrics.jmx.JmxMetricAggregator;
import org.ametiste.metrics.statsd.StatsDMetricAggregator;

import java.util.stream.Stream;

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
