package org.ametiste.metrics.statsd.client;


public interface StatsDClient {

    /**
     * Sends command to increment metric with name {@code name} for value of {@code delta}
     *
     * @param name  name of metric
     * @param delta value of increment
     */
    void increment(String name, int delta);

    /**
     * Sends command to increment metric with name {@code name} for 1
     *
     * @param name name of metric
     */
    void increment(String name);

    /**
     * Sends command to gauge metric with name {@code name} for value of {@code value}
     *
     * @param name  name of metric
     * @param value value
     */
    void gauge(String name, int value);

    /**
     * Sends command to gauge metric with name {@code name} for value of {@code value}
     *
     * @param name  name of metric
     * @param value value
     */
    void gauge(String name, String value);

    /**
     * Sends command to time metric with name {@code name} for value of {@code value}
     *
     * @param name  name of metric
     * @param value value
     */
    void time(String name, int value);
}
