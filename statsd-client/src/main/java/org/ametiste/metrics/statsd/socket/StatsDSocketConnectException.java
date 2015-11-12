package org.ametiste.metrics.statsd.socket;

import java.net.SocketException;

/**
 * Thrown to indicate connection errors in {@link StatsDSocket} implementations
 *
 * @author ametiste
 * @since 0.1.0
 */
public class StatsDSocketConnectException extends RuntimeException {

    public StatsDSocketConnectException(String message) {
        super(message);
    }

    public StatsDSocketConnectException(SocketException e) {
        super(e);
    }
}
