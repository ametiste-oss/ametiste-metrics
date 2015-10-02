package org.ametiste.metrics.resolver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Metric name resolver that resolves path to metric identifier.
 * Mostly is used with metric filters.
 * @author ametiste
 * @since 0.1.0
 */
public class PathMetricsIdentifierResolver implements MetricsIdentifierResolver {

	private final Map<String, String> pathsToId;
    private final String defaultIdentifier;

    /**
     *
     * @param paths list of paths for separate requests metrics count
     * @param defaultIdentifier metric identifier for all requests that dont match one of paths
     */
    public PathMetricsIdentifierResolver(List<String> paths, String defaultIdentifier) {
        this.defaultIdentifier = defaultIdentifier;
        pathsToId = new HashMap<>();
		for (String path : paths) {
            pathsToId.put(path, this.fitName(path));
		}
	}

	private String fitName(String path) {
		return path.replaceAll("/", ".");
	}

	@Override
	public String resolveMetricId(String metricName) {
		if (pathsToId.containsKey(metricName))
			return pathsToId.get(metricName);
		return defaultIdentifier;
	}

}
