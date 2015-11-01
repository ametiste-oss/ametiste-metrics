package org.ametiste.metrics.statsd.socket;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 *
 * @since
 */
public class DatagramStatsDSocketTest {

    private static final int TEST_PORT = 9999;
    private static final String TEST_HOST = "localhost";
    private static final String TEST_MESSAGE = "TEST_MESSAGE";
    @Mock
    private DatagramSocket datagramSocket;
    private DatagramStatsDSocket datagramStatsDSocket;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        datagramStatsDSocket = new DatagramStatsDSocket(TEST_HOST, TEST_PORT, () -> datagramSocket);
    }

    @Test(expected = NullPointerException.class)
    public void testBrokenSocketFactory() throws Exception {

        final DatagramStatsDSocket datagramStatsDSocket =
                new DatagramStatsDSocket(TEST_HOST, TEST_PORT, () -> null);

        datagramStatsDSocket.connect();
    }

    @Test(expected = JustForTestException.class)
    public void testErroredSocketFactory() throws Exception {

        final DatagramStatsDSocket datagramStatsDSocket =
                new DatagramStatsDSocket(TEST_HOST, TEST_PORT, () -> {
                    throw new JustForTestException();
                });

        datagramStatsDSocket.connect();
    }

    @Test
    public void testConnectToFineSocket() throws Exception {

        datagramStatsDSocket.connect();

        final ArgumentCaptor<InetSocketAddress> captor =
                ArgumentCaptor.forClass(InetSocketAddress.class);

        verify(datagramSocket, times(1)).connect(captor.capture());

        assertEquals(TEST_HOST, captor.getValue().getHostName());
        assertEquals(TEST_PORT, captor.getValue().getPort());
    }

    @Test(expected = StatsDSocketConnectException.class)
    public void testConnectToErroredSocket() throws Exception {

        doThrow(SocketException.class)
                .when(datagramSocket).connect(any());

        datagramStatsDSocket.connect();
    }

    @Test(expected = RuntimeException.class)
    public void testConnectRuntumeErroredSocket() throws Exception {

        doThrow(RuntimeException.class)
                .when(datagramSocket).connect(any());

        datagramStatsDSocket.connect();
    }

    @Test
    public void testSendToFineSocket() throws Exception {

        datagramStatsDSocket.connect();
        datagramStatsDSocket.send(TEST_MESSAGE);

        final ArgumentCaptor<DatagramPacket> captor =
                ArgumentCaptor.forClass(DatagramPacket.class);

        verify(datagramSocket, times(1)).send(captor.capture());

        assertEquals(TEST_MESSAGE, new String(captor.getValue().getData()));
    }

    @Test
    public void testSendToDisconnectedSocket() throws Exception {

        /** datagramStatsDSocket.connect(); **/
        datagramStatsDSocket.send(TEST_MESSAGE);

        verify(datagramSocket, times(0)).send(any());
    }

    @Test
    // TODO: fix implementation and unignore
    @Ignore
    public void testSendToClosedSocket() throws Exception {

        datagramStatsDSocket.connect();
        datagramStatsDSocket.close();
        datagramStatsDSocket.send(TEST_MESSAGE);

        verify(datagramSocket, times(0)).send(any());
    }

    @Test
    public void testSendToErroredSocket() throws Exception {

        // NOTE: all exception must be handeled by scoket implementation,
        // so we dont expect any exception here

        doThrow(RuntimeException.class)
                .when(datagramSocket).send(any());

        datagramStatsDSocket.connect();
        datagramStatsDSocket.send(TEST_MESSAGE);

        verify(datagramSocket, times(1)).send(any());
    }

    @Test
    public void testSendNullMessage() throws Exception {

        // NOTE: all exception must be handeled by scoket implementation,
        // so we dont expect any exception here

        datagramStatsDSocket.connect();
        datagramStatsDSocket.send(null);

        verify(datagramSocket, times(0)).send(any());
    }

    @Test
    public void testCloseConnectedSocket() throws Exception {

        datagramStatsDSocket.connect();
        datagramStatsDSocket.close();

        verify(datagramSocket, times(1)).close();
    }

    @Test
    // TODO: fix implementation and unignore
    @Ignore
    public void testCloseClosedSocket() throws Exception {

        datagramStatsDSocket.connect();
        datagramStatsDSocket.close();
        datagramStatsDSocket.close();

        verify(datagramSocket, times(1)).close();
    }

    @Test
    public void testCloseDisconnectedSocket() throws Exception {

        /** datagramStatsDSocket.connect(); **/
        datagramStatsDSocket.close();

        verify(datagramSocket, times(0)).close();
    }

    private class JustForTestException extends RuntimeException {
    }
}