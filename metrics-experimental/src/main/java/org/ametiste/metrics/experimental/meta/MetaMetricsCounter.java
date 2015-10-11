package org.ametiste.metrics.experimental.meta;

import org.ametiste.metrics.MetricsService;
import org.ametiste.metrics.experimental.activator.ActivationCondition;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * <p>
 *      Meta-metric service provides performance metric of enclosed metrics service.
 * </p>
 *
 * <p>
 *     Usable when information about metric service performance is required, say
 *     when new metrics or aggregator introduced.
 * </p>
 *
 * <p>
 *     Usually this service may be disabled, but it may be helpful in the performance
 *     test environment to gather information about metrics performance and usage statistic.
 * </p>
 *
 * todo : special prefix and namespace
 * todo : configuration that allow to choose enabled this service or not
 *
 * @since 0.2.0
 */
public class MetaMetricsCounter implements MetricsService {

    private interface Action {
        void peroform();
    }

    /**
     * MetricsService used to collect meta-metrics.
     */
    private final MetricsService metaMetricsService;

    /**
     * MetricsService that become target of meta-metrics measurements.
     */
    private final MetricsService enclosedMetricsService;

    public MetaMetricsCounter(MetricsService enclosedMetricsService) {
        this(enclosedMetricsService, enclosedMetricsService);
    }

    public MetaMetricsCounter(MetricsService enclosedMetricsService, MetricsService metaMetricsService) {
        this.enclosedMetricsService = enclosedMetricsService;
        this.metaMetricsService = metaMetricsService;
    }

    @Override
    public void increment(String metricId) {
        measureTime("incriment", () -> enclosedMetricsService.increment(metricId));
    }

    @Override
    public void increment(String metricId, int incrementValue) {
        measureTime("incriment-valued", () -> enclosedMetricsService.increment(metricId, incrementValue));
    }

    @Override
    public void createEvent(String metricId, long startValue, long endValue) {
        measureTime("event-range", () -> enclosedMetricsService.createEvent(metricId, startValue, endValue));
    }

    @Override
    public void createEvent(String metricId, int eventValue) {
        measureTime("event", () -> enclosedMetricsService.createEvent(metricId, eventValue));
    }

    private void measureTime(String name, Action action) {
        long startTime = System.currentTimeMillis();
        try {
            action.peroform();
        } finally {
            long endTime = System.currentTimeMillis();
            // TODO: how I should compose meta-name?
            // TODO: Could I have different measurements strategies?
            // TODO: Say, overall measurement for any invocation or
            // TODO: Separate measurement for each concrete metric?
            // TODO: Could I use multiple measurement at the same time?

            // TODO: prefix parameter
            metaMetricsService.createEvent("meta." + name, (int)(endTime - startTime));
        }
    }

}
