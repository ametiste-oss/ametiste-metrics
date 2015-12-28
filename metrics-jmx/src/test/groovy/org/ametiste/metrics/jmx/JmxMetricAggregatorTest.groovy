package org.ametiste.metrics.jmx

import spock.lang.Specification

/**
 * Created by ametiste on 12/28/15.
 */
class JmxMetricAggregatorTest extends Specification {

    private JmxMetricAggregator aggregator = new JmxMetricAggregator("domain", true);

    def gaugeTwice() {
        when: "gauge is called twice "
            aggregator.gauge("metricName", 1);
            aggregator.gauge("metricName", 2);
        then: "no error should happen"

    }

}
