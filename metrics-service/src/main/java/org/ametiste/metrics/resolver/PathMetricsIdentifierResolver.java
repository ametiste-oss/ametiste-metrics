package org.ametiste.metrics.resolver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Metric name resolver that resolves path to metric identifier.
 * Mostly is used with metric filters.
 *
 * @author ametiste
 * @since 0.1.0
 */
public class PathMetricsIdentifierResolver implements MetricsIdentifierResolver {

    private final Map<String, String> pathsToId;
    private final String defaultIdentifier;

    /**
     * @param paths             list of paths for separate requests metrics count
     * @param defaultIdentifier metric identifier for all requests that dont match one of paths
     */
    public PathMetricsIdentifierResolver(List<String> paths, String defaultIdentifier) {

        if(defaultIdentifier==null || defaultIdentifier.isEmpty()) {
            throw new IllegalArgumentException("Default metric identifier cant be empty or null");
        }
        if(paths==null) {
            throw new IllegalArgumentException("Paths cant be null. Use empty list if required");
        }

        this.defaultIdentifier = defaultIdentifier;
        pathsToId = new HashMap<>();
        for (String path : paths) {
            String trimmed = trimEnclosingPath(path);
            pathsToId.put(trimmed, this.fitName(trimmed));
        }
    }

    private String fitName(String path) {
        return path.replaceAll("/", ".");
    }

    @Override
    public String resolveMetricId(String metricName) {

        metricName = trimEnclosingPath(metricName);

        if (pathsToId.containsKey(metricName))
            return pathsToId.get(metricName);
        return defaultIdentifier;
    }

    private String trimEnclosingPath(String path) {

        if(path.startsWith("/")) {
            path = path.replaceFirst("/","");
        }
        if(path.endsWith("/")) {
            path = path.substring(0, path.length()-1);
        }
        return path;
    }

}
