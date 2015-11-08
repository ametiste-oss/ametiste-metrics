package org.ametiste.metrics.router

import org.ametiste.metrics.MetricsAggregator
import spock.lang.Specification

/**
 * Created by atlantis on 11/1/15.
 */
class MappingAggregatorsRouterTest extends Specification {

    Map<String, List<MetricsAggregator>> map = Mock()

    def initialization() {
        when: "mapping aggregators router is initialized with null"
            new MappingAggregatorsRouter(null)
        then: "exception is thrown"
            thrown(IllegalArgumentException)
        when: "aggregators router is initialized with map without __default key"
            map.containsKey("__default") >> false
            new MappingAggregatorsRouter(map)
        then: "exception is thrown"
            thrown(IllegalArgumentException)
    }

    def routingWithoutWildCards() {
        given: "router without wildcards"
            map.containsKey("__default") >> true
            map.keySet() >> ["metric1", "metric2"]
            def router = new MappingAggregatorsRouter(map)
            def metricId = "metric1"
        when: "aggregators for metricId is requested"
            List<MetricsAggregator> aggregators = Mock()
            map.containsKey(metricId) >> true
            map.get(metricId) >> aggregators
            def a = router.getAggregatorsForMetric(metricId)
        then: "list of aggregators should be as expected"
            aggregators == a
    }

    def routingWithoutWildCardsWithDefault() {
        given: "router without wildcards"
            map.containsKey("__default") >> true
            map.keySet() >> ["metric1", "metric2"]
            def router = new MappingAggregatorsRouter(map)
            def metricId = "metric1"
        when: "aggregators for metricId is requested "
            List<MetricsAggregator> aggregators = Mock()
            map.containsKey(metricId) >> false
            map.get("__default") >> aggregators
            def a = router.getAggregatorsForMetric(metricId)
        then: "list of aggregators should be as expected"
            aggregators == a
    }

    def routingWithWildCardsWithDefault() {
        given: "router without wildcards"
            map.containsKey("__default") >> true
            map.keySet() >> ["metric*", "metric2"]
            def router = new MappingAggregatorsRouter(map)
            def metricId = "customMetric1"
        when: "aggregators for metricId is requested "
            List<MetricsAggregator> aggregators = Mock()
            map.keySet() >> ["metric*", "metric2"]
            map.get("__default") >> aggregators
            def a = router.getAggregatorsForMetric(metricId)
        then: "list of aggregators should be as expected"
            aggregators == a
    }

    def routingWithWildCards() {
        given: "router without wildcards"
            map.containsKey("__default") >> true
            map.keySet() >> ["metric*", "metric2"]
            def router = new MappingAggregatorsRouter(map)
            def metricId = "metric1"
        when: "aggregators for metricId is requested "
            List<MetricsAggregator> aggregators = Mock()
            map.keySet() >> ["metric*", "metric2"]
            map.get("metric*") >> aggregators
            def a = router.getAggregatorsForMetric(metricId)
        then: "list of aggregators should be as expected"
            aggregators == a
    }
}
