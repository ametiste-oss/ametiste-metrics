package org.ametiste.metrics.router;

import org.ametiste.metrics.MetricsAggregator;
import org.ametiste.metrics.container.ListContainer;
import org.ametiste.metrics.container.MapContainer;

import java.util.List;
import java.util.Map;

/**
 * {@link AggregatorsRouter} implementation based on {@link MapContainer}
 * Metric names in routing map may contain wildcards.
 * Routing map should contain route with key "__default".
 * Its possible to have only "__default" route.
 * @since 0.1.0
 * @author ametiste
 */
public class MappingAggregatorsRouter implements AggregatorsRouter {
	
	private final String defaultRouteName = "__default";
	private final Map<String, ListContainer> aggregatorsMap;
	private boolean hasWildCards = false;


	/**
	 * Requires routing map {@link MapContainer}  with "__default" key route at least.
	 * @param container - routing map container
	 */
	public MappingAggregatorsRouter(MapContainer container) {
		if(container == null || !container.loadMap().containsKey(defaultRouteName)) {
			throw new IllegalArgumentException("Default routing should be set, use key '" + defaultRouteName + "'");
		}
		container.loadMap().keySet().stream().filter(key -> key.contains("*")).forEach(key ->
				hasWildCards = true);
		this.aggregatorsMap = container.loadMap();
	}

	/**
	 * Matches list of aggregators either by key or by prefixed key. Example of keys:
	 * "metric.that.needs.to.be.routed" matches as to key "metric.that.needs.to.be.routed" as to
	 * "metric.that.needs*" and "*"
	 * when "metric.is.to.be.routed" matches only "*" of those route keys
	 *
	 * @param metricName - name of metric for that route is defined. Note: name of metric means
	 *   already resolved one, in case if {@link org.ametiste.metrics.resolver.MetricsNameResolver}
	 *   had a match for metric identifier, name might be different from identifier
	 * @return - list of  {@link MetricsAggregator} that accept metric with name metricName
	 * if metricName didnt match any specific routes, default route aggregators are returned.
	 */
	@Override
	public List<MetricsAggregator> getAggregatorsForMetric(String metricName) {
		if (aggregatorsMap.containsKey(metricName)) {
			return aggregatorsMap.get(metricName).loadList();
		} else {
			if (hasWildCards) {
				for (String key : aggregatorsMap.keySet()) {
					if (key.contains("*")) {
						if (metricName.startsWith(key.replace("*", ""))) {
							return aggregatorsMap.get(key).loadList();
						}
					}
				}
			}
		}
		return aggregatorsMap.get(defaultRouteName).loadList();
	}

}
