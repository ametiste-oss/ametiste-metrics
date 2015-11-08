package org.ametiste.metrics.statsd

import org.ametiste.metrics.statsd.client.StatsDClient
import spock.lang.Specification

/**
 * Created by atlantis on 10/31/15.
 */
class StatsDMetricsAggregatorTest extends Specification{

    StatsDClient client = Mock()
    StatsDMetricAggregator aggregator = new StatsDMetricAggregator(client);

    def callIncrementOnRandomValue() {
        given: "A value 123 of metric with name 'metric' sent to statsdAggregator"
        def inc = 123;

        when:
        aggregator.increment("metric", inc);

        then: "statsd client should be incremented once with value 123"
        1*client.increment("metric", 123);

    }

    def callGauge() {
        given: "A value 123 of metric with name 'metric' sent as gauge to statsdAggregator"
        def inc = 123;
        when:
        aggregator.gauge("metric", inc);

        then: "statsd client should be gauged once with value 123"
        1*client.gauge("metric", 123);

    }


    def callEvent() {
        given: "A value 123 of metric with name 'metric' sent as event to statsdAggregator"
        def inc = 123;
        when:
        aggregator.event("metric", inc);

        then: "statsd client time should be called once with value 123"
        1*client.time("metric", 123);

    }

}
