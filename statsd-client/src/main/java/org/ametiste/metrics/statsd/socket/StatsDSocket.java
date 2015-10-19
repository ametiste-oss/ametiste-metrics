package org.ametiste.metrics.statsd.socket;

/**
 * Abstraction of socket connection for statsd
 *
 * @author ametiste
 * @since 0.1.0
 */
public interface StatsDSocket {

    void connect() throws StatsDSocketConnectException;

    void send(String message);

    void close();
}
