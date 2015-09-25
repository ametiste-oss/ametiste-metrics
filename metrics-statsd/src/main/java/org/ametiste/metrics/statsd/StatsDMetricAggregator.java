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
	public void increment(String metricName) {
		client.increment(metricName);

	}

	@Override
	public void event(String toSend, int value) {
		client.time(toSend, value);

	}

    @Override
    public void increment(String metricName, int inc) {
        client.increment(metricName, inc);
    }
}
