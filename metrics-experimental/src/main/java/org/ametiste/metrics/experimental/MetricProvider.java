package org.ametiste.metrics.experimental;

public interface MetricProvider {

    void provideMetricValues(MetricConsumer consumer);

}
