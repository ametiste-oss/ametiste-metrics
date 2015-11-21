package org.ametiste.metrics.router;

import org.ametiste.metrics.MetricsAggregator;
import org.ametiste.metrics.resolver.MetricsIdentifierResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * {@link AggregatorsRouter} implementation based on routing map,
 * <p>
 * Metric ids in routing map may contain wildcards.
 * <p>
 * Routing map should contain route with key "__default".
 * <p>
 * Its possible to have only "__default" route.
 *
 * @author ametiste
 * @since 0.1.0
 */
public class MappingAggregatorsRouter implements AggregatorsRouter {

    public final static String DEFAULT_ROUTE_NAME = "__default";

    private final Map<String, List<MetricsAggregator>> aggregatorsMap;

    private boolean hasWildCards = false;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Requires routing map with "__default" key route at least.
     *
     * @param aggregators - routing map aggregators
     */
    public MappingAggregatorsRouter(Map<String, List<MetricsAggregator>> aggregators) {
        if (aggregators == null || !aggregators.containsKey(DEFAULT_ROUTE_NAME)) {
            throw new IllegalArgumentException("Default routing should be set, use key '" + DEFAULT_ROUTE_NAME + "'");
        }
        aggregators.keySet().stream().filter(key -> key.contains("*"))
                .findAny().ifPresent((wildcard) -> hasWildCards = true);
        this.aggregatorsMap = aggregators;
    }

    /**
     * Matches list of aggregators either by key or by prefixed key. Example of keys:
     * "metric.that.needs.to.be.routed" matches as to key "metric.that.needs.to.be.routed" as to
     * "metric.that.needs*" and "*"
     * when "metric.is.to.be.routed" matches only "*" of those route keys
     *
     * @param metricIdentifier - id of metric for that route is defined. Note: id is
     *                         already resolved one, in case if {@link MetricsIdentifierResolver}
     *                         had a match for metric identifier, identifier might be different from initial name
     * @return - list of  {@link MetricsAggregator} that accept metric with id metricIdentifier
     * if metricIdentifier didnt match any specific routes, default route aggregator list is returned.
     */
    @Override
    @Deprecated
    //change visibility, the logic is kept but it should not be part of interface in future
    public List<MetricsAggregator> getAggregatorsForMetric(String metricIdentifier) {
        if (aggregatorsMap.containsKey(metricIdentifier)) {
            return aggregatorsMap.get(metricIdentifier);
        } else {
            if (hasWildCards) {
                return aggregatorsMap.keySet().stream()
                        .filter((key) -> key.contains("*") && metricIdentifier.startsWith(key.replace("*", "")) )
                        .findFirst()
                        .map(aggregatorsMap::get)
                        .orElse(aggregatorsMap.get(DEFAULT_ROUTE_NAME));
            }
        }
        return aggregatorsMap.get(DEFAULT_ROUTE_NAME);
    }

    /**
     * Matches list of aggregators either by key or by prefixed key. Example of keys:
     * "metric.that.needs.to.be.routed" matches as to key "metric.that.needs.to.be.routed" as to
     * "metric.that.needs*" and "*"
     * when "metric.is.to.be.routed" matches only "*" of those route keys
     *
     * @param metricIdentifier - id of metric for that route is defined. Note: id is
     *                         already resolved one, in case if {@link MetricsIdentifierResolver}
     *                         had a match for metric identifier, identifier might be different from initial name
     * @param function - consumer that should be applied to each of aggregators .
     */
    @Override
    public void aggregate(String metricIdentifier, Consumer<MetricsAggregator> function) {
        this.getAggregatorsForMetric(metricIdentifier).forEach(a -> {
            try {
                function.accept(a);
            } catch (Exception e) {
                logger.debug("Aggregator threw exception. Aggregator: " + a.getClass(), e);
            }

        });
    }

}
