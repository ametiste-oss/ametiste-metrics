package org.ametiste.metrics.statsd;


import org.ametiste.metrics.MetricsAggregator;
import org.ametiste.metrics.statsd.client.StatsDClient;

/**
 * MetricAggregator for statsd, via {@link StatsDClient}
 */
public class StatsDMetricAggregator implements MetricsAggregator {

    private StatsDClient client;

    public StatsDMetricAggregator(StatsDClient client) {

        if(client==null) {
            throw new IllegalArgumentException("Client for aggregator cant be null");
        }
        this.client = client;
    }


    @Override
    public void gauge(String metricId, int gaugeValue) {
        client.gauge(metricId, gaugeValue);
    }

    @Override
    public void event(String metricId, int eventValue) {
        client.time(metricId, eventValue);

    }

    @Override
    public void increment(String metricId, int inc) {
        client.increment(metricId, inc);
    }
}
