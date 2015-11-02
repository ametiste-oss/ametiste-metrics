package org.ametiste.metrics

import spock.lang.Specification

/**
 * Created by atlantis on 11/1/15.
 */
class NullMetricsAggregatorTest extends Specification {

    NullMetricsAggregator aggregator = new NullMetricsAggregator()

    def gauge() {
        when: "gauge is called"
            aggregator.gauge("",4)
        then: "nothing happens"

    }

    def event() {
        when: "event is called"
            aggregator.event("",4)
        then: "nothing happens"
    }

    def increment() {
        when: "increment is called"
            aggregator.increment("",4)
        then: "nothing happens"
    }
}
