package org.ametiste.metrics.resolver;

import java.util.Map;

/**
 * Implementation with explicit mapping of id to name resolve entries
 * {@inheritDoc}
 * @since 0.1.0
 * @author ametiste
 */
public class MappingMetricsNameResolver implements MetricsNameResolver {

	private final Map<String, String> metricNames;

	public MappingMetricsNameResolver(Map<String, String> idToNameMap) {
		if(idToNameMap == null) {
			throw new IllegalArgumentException("Metrics id to name map cant be null. " +
					"Use PlainMetricNameResolver if there's no need in explicit mappings");
		}
		this.metricNames = idToNameMap;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getMetricName(String metricIdentifier) {
		if (metricNames.containsKey(metricIdentifier))
			return metricNames.get(metricIdentifier);
		else
			return metricIdentifier;

	}

}
