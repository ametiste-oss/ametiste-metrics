package org.ametiste.metrics.router;

import org.ametiste.metrics.MetricsAggregator;
import org.ametiste.metrics.resolver.MetricsIdentifierResolver;

import java.util.List;

/**
 * Metrics allow configuration with different routes for specific metrics.
 * Defines what MetricAggregators are applied to metric
 *
 * @author ametiste
 * @since 0.1.0
 */
public interface AggregatorsRouter {

    /**
     * Defines list of {@link MetricsAggregator} that may accept metric with id metricIdentifier.
     * Metrics allow configuration with different routes for specific metrics
     *
     * @param metricIdentifier - identifier of metric for that route is defined. Note: id is
     *                         already resolved one, in case if {@link MetricsIdentifierResolver}
     *                         had a match for metric identifier, id might be different from initial name
     * @return list of  {@link MetricsAggregator} that accept metric with id metricIdentifier
     */
    List<MetricsAggregator> getAggregatorsForMetric(String metricIdentifier);

}
