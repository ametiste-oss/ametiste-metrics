package org.ametiste.metrics.prometheus;

import org.ametiste.metrics.MetricsAggregator;

public class PrometheusMetricAggregator implements MetricsAggregator {


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