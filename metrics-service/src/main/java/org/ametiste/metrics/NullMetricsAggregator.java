package org.ametiste.metrics;

/**
 * This aggregator implementation does nothing with metrics.
 * Its created to guarantee that default configuration is launched even if both default aggregators are disabled.
 * Created by ametiste on 10/2/15.
 */
public class NullMetricsAggregator implements MetricsAggregator {


    @Override
    public void gauge(String metricId, int gaugeValue) {

    }

    @Override
    public void event(String metricId, int eventValue) {

    }

    @Override
    public void increment(String metricId, int inc) {

    }
}
