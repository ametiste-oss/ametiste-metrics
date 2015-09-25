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
     * Updates time for metric with id metricId
     * @param metricId identifier of metric
     * @param startTime logged event start time
     * @param endTime logged event end time
     */
	void updateTime(String metricId, long startTime, long endTime);

}
