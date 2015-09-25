package org.ametiste.metrics.experimental;

/**
 *
 * @since
 */
public interface MetricConsumer {

    void consume(String name, String value);

}
