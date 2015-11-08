package org.ametiste.metrics.statsd.socket;

import java.net.DatagramSocket;
import java.net.SocketException;

public interface DatagramSocketFactory {
    DatagramSocket create() throws SocketException;
}
