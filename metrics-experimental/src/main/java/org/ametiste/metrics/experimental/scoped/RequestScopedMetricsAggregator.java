package org.ametiste.metrics.experimental.scoped;

import org.ametiste.metrics.MetricsAggregator;
import org.ametiste.metrics.experimental.activator.conditions.scopes.request.RequestScopeDetector;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.Integer;import java.lang.Override;import java.lang.String;import java.util.HashMap;
import java.util.Optional;
import java.util.function.BiConsumer;

/**
 *
 * @since
 */
// TODO: aggregator activator, something to check request parameters for magic one
// TODO: I would like to do it as decorator
public class RequestScopedMetricsAggregator implements MetricsAggregator {

    private HashMap<String, Integer> requestMetrics = new HashMap<>();

    @Override
    public void increment(String metricId) {

        if (RequestScopeDetector.isNotRequestScoped()) {
            return;
        }

        increment(metricId, 1);
    }

    @Override
    public void event(String metricId, int evenValue) {

        if (RequestScopeDetector.isNotRequestScoped()) {
            return;
        }

        requestMetrics.put(metricId, evenValue);
    }

    @Override
    public void increment(String metricId, int inc) {

        if (RequestScopeDetector.isNotRequestScoped()) {
            return;
        }

        if (!requestMetrics.containsKey(metricId)) {
            requestMetrics.put(metricId, inc);
        } else {
            requestMetrics.put(metricId, requestMetrics.get(metricId) + inc);
        }
    }

    /**
     * <p>
     *     Used to consume aggregated metrics. Feeds key-value pairs of metric names and values.
     * </p>
     *
     * @param consumer metrics consumer
     */
    public void consumeMetrics(BiConsumer<String,Integer> consumer) {
        requestMetrics.forEach(consumer);
    }

}
