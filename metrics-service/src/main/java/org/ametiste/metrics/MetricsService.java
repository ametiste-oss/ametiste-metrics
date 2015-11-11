package org.ametiste.metrics;

/**
 * Service for metric registration
 *
 * @author ametiste
 * @since 0.1.0
 */
public interface MetricsService {

    /**
     * Increments counter to incrementValue for metrics with id metricId
     *
     * @param metricId       identifier of metric
     * @param incrementValue delta of increment
     */
    void increment(String metricId, int incrementValue);


    /**
     * Increments gauge counter to gaugeValue for metric with id metricId
     * @param metricId identifier of metric
     * @param gaugeValue gauge value
     * @since 0.2.0
     */
    void gauge(String metricId, int gaugeValue);

    /**
     * Creates event in time with eventValue for metricId.
     * Can be used to register definite values in time or events with time values
     * (time of method call)
     *
     * @param metricId   identifier of metric
     * @param eventValue logged event start time
     */
    void createEvent(String metricId, int eventValue);

}
