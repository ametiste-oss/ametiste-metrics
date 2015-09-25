package org.ametiste.metrics.resolver;

/**
 * Always resolves name from id without modifications
 * @inheritDoc}
 * @since 0.1.0
 * @author ametiste
 */
public class PlainMetricsNameResolver implements MetricsNameResolver {

	/**
	 * Always resolves metricIdentifier to metric name without any modifications.
	 * {@inheritDoc}
	 */
	@Override
	public String getMetricName(String metricIdentifier) {
		return metricIdentifier;

	}

}
