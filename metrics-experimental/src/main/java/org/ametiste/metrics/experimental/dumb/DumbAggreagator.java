package org.ametiste.metrics.experimental.dumb;

import org.ametiste.metrics.MetricsAggregator;import java.lang.Override;import java.lang.String;import java.lang.System;

/**
 * <p>
 *  Hey, it would be useful for manual testing!
 * </p>
 *
 * @since 0.2.0
 */
public class DumbAggreagator implements MetricsAggregator {
    @Override
    public void increment(String metricId) {
        System.out.println("Just Dumb Aggregator");
    }

    @Override
    public void event(String metricId, int evenValue) {
        System.out.println("Just Dumb Aggregator");
    }

    @Override
    public void increment(String metricId, int inc) {
        System.out.println("Just Dumb Aggregator");
    }
}
