package org.ametiste.metrics.boot.configuration.routing;

import org.ametiste.metrics.MetricsAggregator;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * Represents row in the routing map, contains {@link MetricsAggregator} objects
 * assigned to the specifed routing path.
 * </p>
 *
 * @author ametiste
 * @author masted
 * @since 0.2.0
 */
public class Route {

    final String path;

    List<MetricsAggregator> assigned;

    public Route(String path, List<MetricsAggregator> assigned) {
        this.path = path;
        this.assigned = Collections.unmodifiableList(assigned);
    }

    public static final Route create(String path, MetricsAggregator... assigned) {
        return create(path, Arrays.asList(assigned));
    }

    public static final Route create(String path, List<MetricsAggregator> assigned) {
        return new Route(path, assigned);
    }

}
