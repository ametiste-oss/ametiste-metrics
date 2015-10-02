package org.ametiste.metrics.resolver;

/**
 * Always resolves metricName to metric identifier without any modifications.
 * {@inheritDoc}
 * @since 0.1.0
 * @author ametiste
 */
public class PlainMetricsIdentifierResolver implements MetricsIdentifierResolver {

	/**
	 * Always resolves metricName to metric identifier without any modifications.
	 * {@inheritDoc}
	 */
	@Override
	public String resolveMetricId(String metricName) {
		return metricName;

	}

}
