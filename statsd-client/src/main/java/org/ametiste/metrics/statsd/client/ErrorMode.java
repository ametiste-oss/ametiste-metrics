package org.ametiste.metrics.statsd.client;

/**
 * @since 0.1.0
 * @author ametiste
 */
public enum ErrorMode {

    /**
     * Provides a silent error-prone mode, with only log warning message in case of not successful connection.
     */
    MODERATE,

    /**
     * Provides a strict fail-fast mode, throws exception in case if connection is not successful
     */
    STRICT
}
