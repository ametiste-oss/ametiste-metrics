package org.ametiste.metrics;

import org.ametiste.metrics.resolver.MetricsNameResolver;
import org.ametiste.metrics.router.AggregatorsRouter;

/**
 * Default {@link MetricsService} implementation, sending metrics to metrics aggregators by
 * defined routes.
 * @since 0.1.0
 * @author ametiste
 */
public class AggregatingMetricsService implements MetricsService {

	private static final String DELIMITER = ".";
	private MetricsNameResolver resolver;
	private String prefix = "";
	private AggregatorsRouter router;

    /**
     *
     * @param router AggregatorRouter that defines list of aggregators accepting metric with defined name
     * @param resolver metric id to name resolver
     * @param prefix prefix for metrics to be put before its name. Usually is configured for service to
     *               separate it from other metrics in stack
     */
	public AggregatingMetricsService(AggregatorsRouter router, MetricsNameResolver resolver, String prefix ) {
		if (router==null) {
			throw new  IllegalArgumentException("Router can not be null");
		}
		if(resolver ==null) {
			throw new IllegalArgumentException("Resolver can not be null");
		}
		this.resolver = resolver;
		this.router = router;
		if(prefix!=null) {
			this.prefix = prefix;
		}
	}

    /**
     * Increments counter to 1 for metrics with id metricId
     * @param metricId identifier of metric
     */
	@Override
	public void increment(String metricId) {
        //TODO maybe its possible to get rid of 'get'
		router.getAggregatorsForMetric(metricId)
				.forEach(metricAggregator -> metricAggregator.increment(resolve(metricId)));
	}

    /**
     * Increments counter to incrementValue for metrics with id metricId
     * @param metricId identifier of metric
     * @param incrementValue delta of increment
     */
	@Override
    public void increment(String metricId, int incrementValue) {
		router.getAggregatorsForMetric(metricId)
				.forEach(metricAggregator -> metricAggregator.increment(resolve(metricId), incrementValue));
    }

    /**
     * Updates time for metric with id metricId for every aggregator supporting route for metric with metricId
     * @param metricId identifier of metric
     * @param startTime logged event start time
     * @param endTime logged event end time
     */
    @Override
	public void createEvent(String metricId, long startTime, long endTime) {
		router.getAggregatorsForMetric(metricId).forEach(metricAggregator -> metricAggregator.time(resolve(metricId), startTime, endTime));

	}

	@Override
	public void createEvent(String metricId, int eventValue) {

	}

	private String resolve(String metricId) {
		return prefix + DELIMITER + resolver.getMetricName(metricId);
	}


}
