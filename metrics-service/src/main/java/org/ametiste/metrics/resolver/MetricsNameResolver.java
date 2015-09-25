package org.ametiste.metrics.resolver;

/**
 * Resolves metric name from metric identifier, is useful when name of registered metric is required
 * to be different from metric id. (For example if metric is sent from a point with unreachable source code
 * {@link PlainMetricsNameResolver} is default simplest implementation
 * {@link MappingMetricsNameResolver} is implementation when explicit mappings are required
 * @since 0.1.0
 * @author ametiste
 */
public interface MetricsNameResolver {

	/**
	 *
	 * Resolves metric name from metric identifier, is useful when name of registered metric is required
	 * to be different from metric id. (For example if metric is sent from a point with unreachable source code
	 * @param metricIdentifier id of metric
	 * @return resolved metric name if resolved, metricIdentifier otherwise
	 */
	String getMetricName(String metricIdentifier);

}
