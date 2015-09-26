package org.ametiste.metric.jmx;

import com.codahale.metrics.JmxReporter;
import com.codahale.metrics.MetricRegistry;
import org.ametiste.metrics.MetricsAggregator;

import java.util.concurrent.TimeUnit;

/**
 * Metric aggregator for jmx, recommended for developer needs
 * @author ametiste
 * @since 0.1.0
 */
public class JmxMetricAggregator implements MetricsAggregator {

    final MetricRegistry metrics;
    final JmxReporter reporter;

    public JmxMetricAggregator(String domain) {
        metrics = new MetricRegistry();
        reporter = JmxReporter.forRegistry(metrics).inDomain(domain).build();
        reporter.start();
    }

    @Override
    public void increment(String metricName) {
        metrics.counter(metricName).inc();
    }

    @Override
    public void event(String metricName, int value) {
        metrics.timer(metricName).update(value, TimeUnit.MILLISECONDS);
    }

    @Override
    public void increment(String metricName, int inc) {
        metrics.counter(metricName).inc(inc);
    }

    public void destroy() {
        reporter.stop();
    }
}
