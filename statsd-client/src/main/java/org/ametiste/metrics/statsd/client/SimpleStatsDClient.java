package org.ametiste.metrics.statsd.client;


import org.ametiste.metrics.statsd.socket.DatagramStatsDSocket;
import org.ametiste.metrics.statsd.socket.StatsDSocketConnectException;
import org.ametiste.metrics.statsd.socket.StatsDSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Simple {@link StatsDClient} implementation with non-blocking send with help of {@link ExecutorService}
 * via {@link StatsDSocket}
 * @since 0.1.0
 *
 */
public class SimpleStatsDClient implements StatsDClient {

    private static final int SHUTDOWN_TIMEOUT = 30;
    private final ErrorMode mode;
    private final StatsDSocket socket;
    private final ExecutorService executor;

    private boolean isConnected = false;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * This constructor serves as default implementation of SimpleStatsDClient, based on default executor and
     * {@link DatagramStatsDSocket}. In case if alternative implementation is required,
     * {@link #SimpleStatsDClient(StatsDSocket, ExecutorService, ErrorMode)} should be used
     * @param hostname - name or ip of host to connect
     * @param port - port to connect
     * @param mode - mode of connection. {@link ErrorMode#MODERATE} provides a silent error-prone mode, with only
     *             log warning message in case of not successful connection.
     *             {@link ErrorMode#STRICT} throws exception in case if connection is not successful
     */
    public SimpleStatsDClient(String hostname, int port, ErrorMode mode) {
        this(
                new DatagramStatsDSocket(hostname, port),
                Executors.newSingleThreadExecutor(r -> {
                    Thread result = Executors.defaultThreadFactory().newThread(r);
                    result.setName("StatsD-" + result.getName());
                    return result;
                }),
                mode);
    }

    /**
     * This constructor should be used when alternative {@link StatsDSocket} and executor are required.
     * @param socket - {@link StatsDSocket} implementation. Cant be null.
     * @param executor - {@link ExecutorService} for non-blocking send uses. NB! Default executor service
     *                 uses thread factory with custom thread name, custom executor with different naming
     *                 might trouble tracing. Cant be null.
     * @param mode - mode of connection. {@link ErrorMode#MODERATE} provides a silent error-prone mode, with only
     *             log warning message in case of not successful connection.
     *             {@link ErrorMode#STRICT} throws exception in case if connection is not successful. Cant be null
     *  @throws IllegalArgumentException if at least one of passed parameters is null
     */
    public SimpleStatsDClient(StatsDSocket socket, ExecutorService executor, ErrorMode mode) {
        if(socket ==null || executor ==null || mode ==null) {
            throw new IllegalArgumentException("None of parameters can be null");
        }
        this.socket = socket;
        this.executor = executor;
        this.mode = mode;
    }

    /**
     * Init method providing connect to socket
     * @throws StatsDSocketConnectException if {@link StatsDSocket#connect()} threw exception and mode is {@link ErrorMode#STRICT}
     */
    public void start() {

        try {
            socket.connect();
            isConnected = true;
        }
        catch (StatsDSocketConnectException e) {
            if(mode.equals(ErrorMode.STRICT)) {
                throw e;
            }
            else {
                logger.warn("StatsDClient socket was not connected.");
                logger.debug("StatsDClient socket error.", e);
            }
        }
    }

    /**
     * Destroy method providing close of socket and executor proper shutdown
     */
    public void stop() {
        try {
            executor.shutdown();
            executor.awaitTermination(SHUTDOWN_TIMEOUT, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            logger.debug("Executor wasnt shut down properly", e);
        } finally {
            socket.close();
        }
    }

    /**
     * {@inheritDoc}
     */
    public void increment(String name, int delta) {

        send(String.format("%s:%d|c", name, delta));
    }

    /**
     * {@inheritDoc}
     */
    public void increment(String name) {
        increment(name, 1);
    }

    /**
     * {@inheritDoc}
     */
    public void gauge(String name, int value) {

        send(String.format("%s:%d|g", name, value));
    }

    /**
     * {@inheritDoc}
     */
    public void gauge(String name, String value) {
        send(String.format("%s:%s|g", name, value));
    }

    /**
     * {@inheritDoc}
     */
    public void time(String name, int value) {

        send(String.format("%s:%d|ms", name, value));
    }

    private void send(final String message) {
        try {
            executor.execute(() -> {
                if(isConnected) {
                    socket.send(message);
                } else {
                    logger.warn("StatsDClient socket was not connected.");
                }
            });
        } catch (Exception ignored) {
        }
    }

}
