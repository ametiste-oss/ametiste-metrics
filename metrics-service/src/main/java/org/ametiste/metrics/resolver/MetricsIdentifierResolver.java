package org.ametiste.metrics.resolver;

/**
 * Resolves metric identifier from metric name, is useful when id of registered metric is required
 * to be different from metric name. (For example if metric is sent from a point with unreachable source code
 * {@link PlainMetricsIdentifierResolver} is default simplest implementation
 * {@link MappingMetricsIdentifierResolver} is implementation when explicit mappings are required
 * @since 0.1.0
 * @author ametiste
 */
public interface MetricsIdentifierResolver {

	/**
	 *
	 * Resolves metric identifier from metric name, is useful when id of registered metric is required
	 * to be different from metric name. (For example if metric is sent from a point with unreachable source code
	 * @param metricName name of metric
	 * @return resolved metric id if resolved, metricName otherwise
	 */
	String resolveMetricId(String metricName);

}
