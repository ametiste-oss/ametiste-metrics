package org.ametiste.metrics.resolver

import spock.lang.Specification

/**
 * Created by atlantis on 11/1/15.
 */
class PathMetricsIdentifierResolverTest extends Specification {

    List<String> paths = Arrays.asList("/cats","/dogs/double")
    PathMetricsIdentifierResolver resolver = new PathMetricsIdentifierResolver(paths, "defaultName")

    def initialization() {
        when: "Resolver is initialized with null default name"
            new PathMetricsIdentifierResolver(paths, null)
        then: "Exception is thrown"
            thrown(IllegalArgumentException)
        when: "Resolver is initialized with empty default name"
            new PathMetricsIdentifierResolver(paths, "")
        then: "Exception is thrown"
            thrown(IllegalArgumentException)
        when: "Resolver is initialized with null paths list"
            new PathMetricsIdentifierResolver(null, "defaultName")
        then: "Exception is thrown"
            thrown(IllegalArgumentException)
        when: "Resolver is initialized with empty paths list"
            new PathMetricsIdentifierResolver(Collections.emptyList(), "defaultName")
        then: "Its legit"
    }

    def resolvePathFromList() {
        given: "metric from locations /cats"
            def metric = "/cats"
        when: "resolving metric"
            def metricId = resolver.resolveMetricId(metric)
        then: "metric should have name cats"
            metricId == "cats"
    }

    def resolvePathFromListEnclosedWithSlash() {
        given: "metric from locations /cats/"
            def metric = "/cats/"
        when: "resolving metric"
            def metricId = resolver.resolveMetricId(metric)
        then: "metric should have name cats"
            metricId == "cats"
    }

    def resolvePathDefault() {
        given: "metric from locations /cats/dogs"
        def metric = "/cats/dogs"
        when: "resolving metric"
        def metricId = resolver.resolveMetricId(metric)
        then: "metric should have default name"
            metricId == "defaultName"
    }

}
