package org.ametiste.metrics

import org.ametiste.metrics.resolver.MetricsIdentifierResolver
import org.ametiste.metrics.router.AggregatorsRouter
import spock.lang.Specification


/**
 * Created by atlantis on 10/31/15.
 */
class AggregatingMetricsSerivceTest extends Specification {

    AggregatorsRouter aggregatorsRouter = Mock()
    MetricsIdentifierResolver resolver = Mock()
    AggregatingMetricsService service = new AggregatingMetricsService(aggregatorsRouter, resolver, "")
    MetricsAggregator aggregatorOne = Mock()
    MetricsAggregator aggregatorTwo = Mock()
    List<MetricsAggregator> a = Arrays.asList(aggregatorOne, aggregatorTwo);

    def properInitializaion() {
        given: "initialization of service"

        when: "router is null"
            new AggregatingMetricsService(null, resolver, "")

        then: "exception is thrown"
            thrown(IllegalArgumentException)

        when: "resolver is null"
            new AggregatingMetricsService(aggregatorsRouter, null, "")
        then: "exception is thrown"
            thrown(IllegalArgumentException)
        when: "prefix is null"
            new AggregatingMetricsService(aggregatorsRouter, resolver, null)
        then: "its allowed"

    }

    def incrementOnRandomValue() {
        given: "value for increment a metric"
            def incrementValue = 3;

        when: "router is resolving any metrics name to list of aggregators a out of 2 aggregators"
            aggregatorsRouter.getAggregatorsForMetric(_) >> a
            service.increment("", incrementValue);

        then: "both aggregators in list should receive increment method call with value 3"
            1* aggregatorOne.increment(_, 3)
            1* aggregatorTwo.increment(_, 3)
    }

    def gauge() {
        given: "value for gauge a metric"
            def incrementValue = 3;

        when: "router is resolving any metrics name to list of aggregators a out of 2 aggregators"
            aggregatorsRouter.getAggregatorsForMetric(_) >> a
            service.gauge("", incrementValue);

        then: "both aggregators in list should receive gauge method call with value 3"
            1* aggregatorOne.gauge(_, 3)
            1* aggregatorTwo.gauge(_, 3)
    }

    def event() {
        given: "value for registering event metric"
            def incrementValue = 3;

        when: "router is resolving any metrics name to list of aggregators a out of 2 aggregators"
            aggregatorsRouter.getAggregatorsForMetric(_) >> a
            service.createEvent("", incrementValue);

        then: "both aggregators in list should receive event method call with value 3"
            1* aggregatorOne.event(_, 3)
            1* aggregatorTwo.event(_, 3)
    }


}
