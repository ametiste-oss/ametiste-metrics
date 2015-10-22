package org.ametiste.metrics.experimental.streams;

import org.ametiste.metrics.experimental.measurement.TimeMeasurement;

/**
 *
 * @since
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

}
