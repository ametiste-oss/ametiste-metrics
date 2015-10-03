package org.ametiste.metrics.experimental;

import org.ametiste.metrics.MetricsAggregator;import java.lang.Override;import java.lang.String;import java.lang.System;

/**
 *
 * @since
 */
public class DumbAggreagator implements MetricsAggregator {
    @Override
    public void increment(String metricId) {
        System.out.println("MMMMM");
    }

    @Override
    public void event(String metricId, int evenValue) {
        System.out.println("MMMMM");
    }

    @Override
    public void increment(String metricId, int inc) {
        System.out.println("MMMMM");
    }
}
