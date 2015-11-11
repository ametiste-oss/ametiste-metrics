package org.ametiste.metrics.resolver

import spock.lang.Specification

/**
 * Created by atlantis on 11/1/15.
 */
class MappingMetricsIdentifierResolverTest extends Specification {

    Map<String, String> map = Mock()
    MappingMetricsIdentifierResolver resolver = new MappingMetricsIdentifierResolver(map)

    def properInitializaion() {
        given: "creating resolver"
        when: "resolver is created with null map"
            new MappingMetricsIdentifierResolver(null)
        then: "exception is thrown"
            thrown(IllegalArgumentException)
    }

    def resolveMetricContainedInMap() {
        given: "metric with name myMetric"
            def metricName = "myMetric"
        when: "map contains name of metric"
            map.containsKey(metricName) >> true
            map.get(metricName) >> "myResolvedMetric"
        then: "name is resolved to map value"
            "myResolvedMetric"== resolver.resolveMetricId(metricName)
    }

    def resolveMetricNotContainedInMap() {
        given: "metric with name myMetric"
        def metricName = "myMetric"
        when: "map contains name of metric"
        map.containsKey(metricName) >> false
        then: "name is resolved to map value"
        "myMetric"== resolver.resolveMetricId(metricName)
    }


}
