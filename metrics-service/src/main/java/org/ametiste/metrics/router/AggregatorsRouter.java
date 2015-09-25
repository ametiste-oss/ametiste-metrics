package org.ametiste.metrics.router;

import org.ametiste.metrics.MetricsAggregator;

import java.util.List;

/**
 * Metrics allow configuration with different routes for specific metrics.
 * Defines what MetricAggregators are applied to metric
 * @since 0.1.0
 * @author ametiste
 */
public interface AggregatorsRouter {

	/**
	 * Defines list of {@link MetricsAggregator} that may accept metric with name metricName.
	 * Metrics allow configuration with different routes for specific metrics
	 * @param metricName - name of metric for that route is defined. Note: name of metric means
	 *   already resolved one, in case if {@link org.ametiste.metrics.resolver.MetricsNameResolver}
	 *   had a match for metric identifier, name might be different from identifier
	 * @return list of  {@link MetricsAggregator} that accept metric with name metricName
	 */
	List<MetricsAggregator> getAggregatorsForMetric(String metricName);

}
