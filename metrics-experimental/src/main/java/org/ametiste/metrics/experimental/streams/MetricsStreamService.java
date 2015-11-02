package org.ametiste.metrics.experimental.streams;

import org.ametiste.metrics.MetricsService;

/**
 *
 * @since
 */
/*

TODO: try to use it with the core metrics service configuration using MetricsServiceStream.

But, need to find the way how to specify which metrics service implementation is used by the aspects.
One option here is write aggregator implementation that relays calls to MetricsStreamService.
And inject this aggregator as @CoreAggregator to core metrics service.

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
        this.metricsStreams.gauge(metricId, gaugeValue);
    }

    @Override
    public void createEvent(String metricId, int eventValue) {
        this.metricsStreams.event(metricId, eventValue);
    }

}
