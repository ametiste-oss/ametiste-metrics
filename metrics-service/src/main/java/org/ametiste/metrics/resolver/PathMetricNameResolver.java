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
public class PathMetricNameResolver implements MetricsNameResolver {

	private final Map<String, String> pathsToId;
    private final String defaultName;

    /**
     *
     * @param paths list of paths for separate requests metrics count
     * @param defaultName metric name for all requests that dont match one of paths
     */
    public PathMetricNameResolver(List<String> paths, String defaultName) {
        this.defaultName = defaultName;
        pathsToId = new HashMap<>();
		for (String path : paths) {
            pathsToId.put(path, this.fitName(path));
		}
	}

	private String fitName(String path) {
		return path.replaceAll("/", ".");
	}

	@Override
	public String getMetricName(String metricIdentifier) {
		if (pathsToId.containsKey(metricIdentifier))
			return pathsToId.get(metricIdentifier);
		return defaultName;
	}

}
