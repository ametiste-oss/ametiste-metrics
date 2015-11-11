package org.ametiste.metrics.jmx;

import com.codahale.metrics.Gauge;
import com.codahale.metrics.JmxReporter;
import com.codahale.metrics.MetricRegistry;
import org.ametiste.metrics.MetricsAggregator;

import java.util.concurrent.TimeUnit;

/**
 * Metric aggregator for jmx
 *
 * @author ametiste
 * @since 0.1.0
 */
public class JmxMetricAggregator implements MetricsAggregator {

    private final MetricRegistry metrics;
    private final JmxReporter reporter;

    public JmxMetricAggregator(String domain) {
        metrics = new MetricRegistry();
        reporter = JmxReporter.forRegistry(metrics).inDomain(domain).build();
        reporter.start();
    }

    @Override
    public void gauge(String metricId, int gaugeValue) {
        metrics.register(metricId, (Gauge)() -> gaugeValue);
    }

    @Override
    public void event(String metricId, int eventValue) {
        metrics.timer(metricId).update(eventValue, TimeUnit.MILLISECONDS);
    }

    @Override
    public void increment(String metricId, int inc) {
        metrics.counter(metricId).inc(inc);
    }

    public void destroy() {
        reporter.stop();
    }
}
