package org.ametiste.metrics.experimental.streams;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @since
 */
public class ThreadPoolStream implements MetricsStream {

    // TODO: limited queue executor required
    // TODO: pool size parameter required
    // TODO: thread pool name parameter required?
    private final ExecutorService executorService = Executors.newFixedThreadPool(1);

    private final MetricsStream upstream;

    public ThreadPoolStream(MetricsStream upstream) {
        this.upstream = upstream;
    }

    @Override
    public void increment(String metricId, int value) {
        executorService.submit(
                () -> upstream.increment(metricId, value)
        );
    }

    @Override
    public void event(String metricId, int value) {
        executorService.submit(
                () -> upstream.event(metricId, value)
        );
    }

    @Override
    public void gauge(String metricId, int value) {
        executorService.submit(
                () -> upstream.event(metricId, value)
        );
    }

}
