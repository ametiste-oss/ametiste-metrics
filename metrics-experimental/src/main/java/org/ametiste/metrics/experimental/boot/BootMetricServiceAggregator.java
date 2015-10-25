package org.ametiste.metrics.experimental.boot;

import org.ametiste.metrics.MetricsAggregator;
import org.springframework.boot.actuate.metrics.CounterService;

/**
 *
 * @since
 */
public class BootMetricServiceAggregator implements MetricsAggregator {

    // TODO: hmss, CounterService appends counter. prefix to the metric name :/
    private final CounterService counterService;

    public BootMetricServiceAggregator(CounterService counterService) {
        this.counterService = counterService;
    }


    @Override
    public void gauge(String metricId, int gaugeValue) {

    }

    @Override
    public void event(String metricId, int evenValue) {

    }

    @Override
    public void increment(String metricId, int inc) {
        for (int i = 0; i < inc; i++) {
            counterService.increment(metricId);
        }
    }
}
