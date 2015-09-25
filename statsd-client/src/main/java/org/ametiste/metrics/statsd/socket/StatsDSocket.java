package org.ametiste.metrics.statsd.socket;

/**
 * Abstraction of socket connection for statsd
 *
 * @since 0.1.0
 * @author ametiste
 */
public interface StatsDSocket {

    void connect() throws StatsDSocketConnectException;
    void send(String message);
    void close();
}
