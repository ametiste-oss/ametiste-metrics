package org.ametiste.metrics.resolver;

import java.util.Map;

/**
 * Implementation with explicit mapping of name to id resolve entries
 * {@inheritDoc}
 *
 * @author ametiste
 * @since 0.1.0
 */
public class MappingMetricsIdentifierResolver implements MetricsIdentifierResolver {

    private final Map<String, String> nameToIdMap;

    public MappingMetricsIdentifierResolver(Map<String, String> nameToIdMap) {
        if (nameToIdMap == null) {
            throw new IllegalArgumentException("Metrics id to name map cant be null. " +
                    "Use PlainMetricNameResolver if there's no need in explicit mappings");
        }
        this.nameToIdMap = nameToIdMap;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String resolveMetricId(String metricName) {
        if (nameToIdMap.containsKey(metricName))
            return nameToIdMap.get(metricName);
        else
            return metricName;

    }

}
