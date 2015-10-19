package org.ametiste.metrics.boot.configuration.routing;

import org.ametiste.metrics.MetricsAggregator;

import java.util.*;

/**
 * <p>
 * Represents routing configuration, route routing, that would be used
 * during routing service configuration to define how concrete metrics are handeled.
 * </p>
 * <p>
 * <p>
 * Each route configuration contains list of {@link Route} objects mapped to route paths.
 * </p>
 * <p>
 * <p>
 * To configure routing just define route paths and routes to this paths, for example
 * </p>
 * <p>
 * <pre>
 * new RouteConfiguration(
 *      Route.create("org.ame.controller.*", jmxAggregator),
 *      Route.create("org.ame.service.*", statsdAggregator, jmxAggregator),
 * );
 * </pre>
 *
 * @author ametiste
 * @author masted
 * @since 0.2.0
 */
public class RouteConfiguration {

    private Map<String, List<MetricsAggregator>> routing = new HashMap<>();

    public RouteConfiguration(List<Route> routes) {

        if (routes == null) {
            throw new IllegalArgumentException("Routes list can't be null.");
        }

        routes.forEach(this::assign);
        // NOTE: finalizing routing map
        this.routing = Collections.unmodifiableMap(routing);
    }

    public RouteConfiguration(Route... routes) {
        this(Arrays.asList(routes));
    }

    public Map<String, List<MetricsAggregator>> asMap() {
        return routing;
    }

    private void assign(Route route) {
        routing
                .computeIfAbsent(route.path, k -> new ArrayList<>())
                .addAll(route.assigned);
    }

}
