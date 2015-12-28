package org.ametiste.metrics.jmx;

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
    private final boolean gaugeEnabled;

    public JmxMetricAggregator(String domain, boolean gaugeEnabled) {
        this.gaugeEnabled = gaugeEnabled;
        metrics = new MetricRegistry();
        reporter = JmxReporter.forRegistry(metrics).inDomain(domain).build();
        reporter.start();
    }

    @Override
    public void gauge(String metricId, int gaugeValue) {
        //NOTE! experimental method that should be checked in production, by default gauge is disabled in jmx
        if(gaugeEnabled) {
            doGauge(metricId, gaugeValue);
        }
    }

    private void doGauge(String metricId, int gaugeValue) {
        if(!metrics.getGauges().containsKey(metricId)) {
            metrics.register(metricId, new UpdateableGauge(gaugeValue));
        } else {
            ((UpdateableGauge)(metrics.getGauges().get(metricId))).update(gaugeValue);
        }
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
