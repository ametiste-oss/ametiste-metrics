package org.ametiste.metrics.experimental.boot;

import org.ametiste.metrics.MetricsAggregator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;

/**
 *
 * @since
 */
public class BootMetricServiceAggregator implements MetricsAggregator {

    public static final int INCREMENT_THRESHOLD = 10;

    // TODO: hmss, CounterService appends counter. prefix to the metric name :/
    private final CounterService counterService;

    private final GaugeService gaugeService;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public BootMetricServiceAggregator(CounterService counterService, GaugeService gaugeService) {
        this.counterService = counterService;
        this.gaugeService = gaugeService;
    }

    @Override
    public void gauge(String metricId, int gaugeValue) {
        gaugeService.submit(metricId, gaugeValue);
    }

    @Override
    public void event(String metricId, int eventValue) {

        // NOTE: log warnigns only if debug logging is enabled to not overload log output
        if (logger.isDebugEnabled()) {
            logger.warn("Event metric '" + metricId + "' skiped.");
            logger.warn("spring-boot metrics service does not provide any usable protocol to store events");
        }
    }

    @Override
    public void increment(String metricId, int inc) {

        if (inc >= INCREMENT_THRESHOLD) {
            // NOTE: log warnigns only if debug logging is enabled to not overload log output
            if (logger.isDebugEnabled()) {
                logger.warn("Incremental metric '" + metricId + "' skiped.");
                logger.warn("BootMetricServiceAggregator#increment " +
                        "implementation will take more than 10 calls of underlying method.");
                logger.warn("Use metric routing to exclude this metric from boot aggreagtion");
                logger.warn("Or reimplement it.");
            }
            return;
        }

        // NOTE: spring-boot CounterService does not support increment valu, so we must iterate over it
        for (int i = 0; i < inc; i++) {
            counterService.increment(metricId);
        }
    }
}
