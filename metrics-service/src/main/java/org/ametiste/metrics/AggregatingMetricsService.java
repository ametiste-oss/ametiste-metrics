package org.ametiste.metrics;

import org.ametiste.metrics.resolver.MetricsIdentifierResolver;
import org.ametiste.metrics.router.AggregatorsRouter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

/**
 * Default {@link MetricsService} implementation, sending metrics to metrics aggregators by
 * defined routes.
 *
 * @author ametiste
 * @since 0.1.0
 */
public class AggregatingMetricsService implements MetricsService {

    private static final String DELIMITER = ".";
    private MetricsIdentifierResolver resolver;
    private String prefix = "";
    private AggregatorsRouter router;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * @param router   AggregatorRouter that defines list of aggregators accepting metric with defined name
     * @param resolver metric name to identifier resolver
     * @param prefix   prefix for metrics to be put before its name. Usually is configured for service to
     *                 separate it from other metrics in stack
     */
    public AggregatingMetricsService(AggregatorsRouter router, MetricsIdentifierResolver resolver, String prefix) {
        if (router == null) {
            throw new IllegalArgumentException("Router can not be null");
        }
        if (resolver == null) {
            throw new IllegalArgumentException("Resolver can not be null");
        }
        this.resolver = resolver;
        this.router = router;
        if (prefix != null) {
            this.prefix = prefix;
        }
    }

    /**
     * Increments counter to incrementValue for metrics with id metricId
     *
     * @param metricId       identifier of metric
     * @param incrementValue delta of increment
     */
    @Override
    public void increment(String metricId, int incrementValue) {
        route(metricId, metricAggregator -> metricAggregator.increment(resolve(metricId), incrementValue));
    }

    /**
     * Increments gauge counter to gaugeValue for metric with id metricId
     * @param metricId identifier of metric
     * @param gaugeValue gauge value
     * @since 0.2.0
     */
    @Override
    public void gauge(String metricId, int gaugeValue) {
        route(metricId, metricAggregator -> metricAggregator.gauge(resolve(metricId), gaugeValue));
     }


    /**
     * Creates event in time with eventValue for metricId.
     * Can be used to register definite values in time or events with time values
     * (time of method call)
     *
     * @param metricId   identifier of metric
     * @param eventValue logged event start time
     */
    @Override
    public void createEvent(String metricId, int eventValue) {
        route(metricId, (metricAggregator) ->
                metricAggregator.event(resolve(metricId), eventValue));
    }

    private void route(String metricId, Consumer<MetricsAggregator> action) {
        try {
            router.aggregate(metricId, action);
        }catch (Exception e) {
            // do nothing, our service should not stop flow in common case
            logger.debug("Router threw an exception", e);
        }

    }

    private String resolve(String metricId) {
        return prefix + DELIMITER + resolver.resolveMetricId(metricId);
    }


}
