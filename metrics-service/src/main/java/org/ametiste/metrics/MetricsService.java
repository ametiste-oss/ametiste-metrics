package org.ametiste.metrics;

/**
 *
 * Service for metric registration
 * @since 0.1.0
 * @author ametiste
 *
 */
public interface MetricsService {

    /**
     * Increments counter to 1 for metrics with id metricId
     * @param metricId identifier of metric
     */
	void increment(String metricId);

    /**
     * Increments counter to incrementValue for metrics with id metricId
     * @param metricId identifier of metric
     * @param incrementValue delta of increment
     */
    void increment(String metricId, int incrementValue);

    /**
     * Creates event in time with lower and higher values for metricId.
     * @param metricId identifier of metric
     * @param startValue logged event start time
     * @param endValue logged event end time
     */
    @Deprecated
	void createEvent(String metricId, long startValue, long endValue);

    /**
     * Creates event in time with eventValue for metricId.
     * Can be used to register definite values in time or events with time values
     * (time of method call)
     * @param metricId identifier of metric
     * @param eventValue logged event start time
     */
    void createEvent(String metricId, int eventValue);

}
