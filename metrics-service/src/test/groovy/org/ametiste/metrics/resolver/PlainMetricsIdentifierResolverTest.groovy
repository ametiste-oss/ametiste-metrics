package org.ametiste.metrics.resolver

import spock.lang.Specification

/**
 * Created by atlantis on 11/1/15.
 */
class PlainMetricsIdentifierResolverTest extends Specification {


    def plainResolving() {
        given: "plain resolver"
            def resolver = new PlainMetricsIdentifierResolver()
        when: "resolving metric with any name"
            def resolved = resolver.resolveMetricId("metric")
        then: "same name is returned"
            "metric" == resolved

    }
}
