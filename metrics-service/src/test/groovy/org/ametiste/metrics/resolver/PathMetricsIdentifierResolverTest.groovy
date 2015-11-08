package org.ametiste.metrics.resolver

import spock.lang.Specification

/**
 * Created by atlantis on 11/1/15.
 */
class PathMetricsIdentifierResolverTest extends Specification {

    List<String> paths = Arrays.asList("/cats","/dogs/double")
    PathMetricsIdentifierResolver resolver = new PathMetricsIdentifierResolver(paths, "defaultName")

    def resolvePathFromList() {
        given: "metric from locations /cats"
        def metric = "/cats"
        when: "resolving metric"
            def metricId = resolver.resolveMetricId(metric)
        then: "metric should have name cats"
            ".cats" == metricId
    }

    def resolvePathDefault() {
        given: "metric from locations /cats/dogs"
        def metric = "/cats/dogs"
        when: "resolving metric"
        def metricId = resolver.resolveMetricId(metric)
        then: "metric should have default name"
        "defaultName" == metricId
    }

}
