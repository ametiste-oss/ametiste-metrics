package org.ametiste.metrics.statsd;


import org.ametiste.metrics.MetricsAggregator;
import org.ametiste.metrics.statsd.client.StatsDClient;

/**
 * MetricAggregator for statsd, via {@link StatsDClient}
 */
public class StatsDMetricAggregator implements MetricsAggregator {

	private StatsDClient client;

	public StatsDMetricAggregator(StatsDClient client) {
		this.client = client;
	}

	@Override
	public void increment(String metricId) {
		client.increment(metricId);

	}

	@Override
	public void event(String metricId, int value) {
		client.time(metricId, value);

	}

    @Override
    public void increment(String metricId, int inc) {
        client.increment(metricId, inc);
    }
}
