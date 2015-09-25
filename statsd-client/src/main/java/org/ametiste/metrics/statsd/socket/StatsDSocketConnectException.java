package org.ametiste.metrics.statsd.socket;

import java.net.SocketException;

/**
 * Thrown to indicate connection errors in {@link StatsDSocket} implementations
 * @since 0.1.0
 * @author ametiste
 */
public class StatsDSocketConnectException extends RuntimeException {
    public StatsDSocketConnectException(SocketException e) {
        super(e);
    }
}
