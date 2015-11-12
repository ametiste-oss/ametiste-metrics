package org.ametiste.metrics.statsd.socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.*;

/**
 * Default implementation of {@link StatsDSocket} used for statsd metrics sending based on {@link DatagramSocket}
 *
 * @since 0.1.0
 */
public class DatagramStatsDSocket implements StatsDSocket {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final DatagramSocketFactory sockFactory;

    private InetSocketAddress address;
    private DatagramSocket clientSocket;

    /**
     * Only connection parameters are provided in constructor, no actual connection is performed
     *
     * @param hostname - name or ip of host to connect
     * @param port     - port to connect
     * @throws IllegalArgumentException if the port parameter is outside the range
     *                                  of valid port values, or if the hostname parameter is <TT>null</TT>.
     *                                  (from {@link InetSocketAddress#InetSocketAddress(String, int)}
     */
    public DatagramStatsDSocket(String hostname, int port) {
        this(hostname, port, DatagramSocket::new);
    }

    /**
     * Primarily used to unit test socket implementation,
     * allows to change underlying {@link DatagramSocket} implementation.
     *
     * @param hostname    - name or ip of host to connect
     * @param port        - port to connect
     * @param sockFactory - factory to create {@link DatagramSocket}
     * @throws IllegalArgumentException if the port parameter is outside the range
     *                                  of valid port values, or if the hostname parameter is <TT>null</TT>.
     *                                  (from {@link InetSocketAddress#InetSocketAddress(String, int)}
     */
    DatagramStatsDSocket(String hostname, int port, DatagramSocketFactory sockFactory) {
        this.sockFactory = sockFactory;
        this.address = new InetSocketAddress(hostname, port);
    }

    /**
     * Socket connection attempt with parameters provided in {@link #DatagramStatsDSocket(String, int)}
     *
     * @throws StatsDSocketConnectException - in case {@link DatagramSocket#connect(InetAddress, int)} threw
     *                                      {@link SocketException} or this socket is already connected.
     */
    public void connect() throws StatsDSocketConnectException {

        // NOTE: because connect operation is critical and must be verified by a client through
        // exception handling, failed connection notified by exception
        if (clientSocket != null) {
            throw new StatsDSocketConnectException("Socket already connected to "
                    + address.getHostName() + ":" + address.getPort());
        }

        try {
            this.clientSocket = sockFactory.create();
            this.clientSocket.connect(address);
        } catch (SocketException e) {
            throw new StatsDSocketConnectException(e);
        }
    }

    /**
     * Sends message via {@link DatagramSocket}
     *
     * @param message - message to send
     */
    public void send(String message) {

        if (clientSocket == null) {
            logger.debug("Trying to send to already closed socket. Check your client.");
            return;
        }

        try {
            final byte[] sendData = message.getBytes();
            final DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length);
            clientSocket.send(sendPacket);
        } catch (Exception ignored) {
            logger.debug("No message is sent. Reason: ", ignored);
        }

    }

    /**
     * Closes connection if opened
     */
    public void close() {
        if (clientSocket != null) {
            try {
                clientSocket.close();
            } finally {
                clientSocket = null;
            }
        } else if (logger.isDebugEnabled()) {
            logger.debug("Trying to close already closed socket. Check your client.");
        }
    }
}
