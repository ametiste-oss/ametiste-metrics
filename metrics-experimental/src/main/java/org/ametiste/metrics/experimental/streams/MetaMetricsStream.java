package org.ametiste.metrics.experimental.streams;

import org.ametiste.metrics.experimental.measurement.TimeMeasurement;

/**
 * <p>
 * Meta-metric Stream provides performance metric of enclosed metrics service.
 * </p>
 * <p>
 * <p>
 * Usable when information about metric service performance is required, say
 * when new metrics or aggregator introduced.
 * </p>
 * <p>
 * <p>
 * Usually this service may be disabled, but it may be helpful in the performance
 * test environment to gather information about metrics performance and usage statistic.
 * </p>
 * <p>
 *
 * todo : special prefix and namespace
 * todo : configuration that allow to choose enabled this service or not
 *
 * @since 0.2.0
 */
public class MetaMetricsStream implements MetricsStream {

    public final static String META_INCREMENT_METRIC = "ame.metrics.meta.increment";

    public final static String META_EVENT_METRIC = "ame.metrics.meta.event";

    private final MetricsStream upstream;

    private final TimeMeasurement timeMeasurement;

    public MetaMetricsStream(MetricsStream upstream, MetricsStream metricsStream) {
        this.upstream = upstream;
        this.timeMeasurement = new TimeMeasurement(
                (n, v) -> metricsStream.event(n,v)
        );
    }

    @Override
    public void increment(String metricId, int value) {
        this.timeMeasurement
                .measureTimeOf(META_INCREMENT_METRIC, () -> upstream.increment(metricId, value));
    }

    @Override
    public void event(String metricId, int value) {
        this.timeMeasurement
                .measureTimeOf(META_EVENT_METRIC, () -> upstream.event(metricId, value));
    }

    @Override
    public void gauge(String metricId, int value) {
        this.timeMeasurement
                .measureTimeOf(META_EVENT_METRIC, () -> upstream.gauge(metricId, value));
    }

}
