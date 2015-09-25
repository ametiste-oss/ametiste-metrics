package org.ametiste.metrics.statsd.client;

import org.ametiste.metrics.statsd.socket.StatsDSocketConnectException;
import org.ametiste.metrics.statsd.socket.StatsDSocket;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import static org.mockito.Mockito.*;


/**
 * Created by ametiste on 9/18/15.
 *
 */
public class SimpleStatsDClientTest {

    private SimpleStatsDClient client;

    @Mock
    private StatsDSocket socket;

    /**
     * Should be only changed from single thread with CAUTION for test use. look {@link #waitBeforeVerify()}
     */
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        client = new SimpleStatsDClient(socket, executorService, ErrorMode.MODERATE);
    }

    @After
    public void tearDown() {
        Mockito.reset(socket);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalParametersSocket() throws Exception {
        new SimpleStatsDClient(null, executorService, ErrorMode.MODERATE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalParametersExecutor() throws Exception {
        new SimpleStatsDClient(socket, null, ErrorMode.MODERATE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalParametersMode() throws Exception {
        new SimpleStatsDClient(socket,executorService, null);
    }

    @Test
    public void testStart() throws Exception {
        client = new SimpleStatsDClient(socket, executorService, ErrorMode.MODERATE);
        client.start();
        verify(socket, times(1)).connect();
    }

    @Test
    public void testStartWithFailConnectionAndSilentMode() throws Exception {
        Mockito.doThrow(StatsDSocketConnectException.class).when(socket).connect();
        client.start();
        verify(socket, times(1)).connect();
    }

    @Test(expected = StatsDSocketConnectException.class)
    public void testStartWithFailConnectionAndVerboseMode() throws Exception {
        client = new SimpleStatsDClient(socket,executorService, ErrorMode.STRICT);
        doThrow(StatsDSocketConnectException.class).when(socket).connect();
        client.start();
        verify(socket, times(1)).connect();
    }

    @Test
    public void testStop() throws Exception {
        client.stop();
        verify(socket, times(1)).close();
    }

    @Test
    public void testIncrement() throws Exception {
        client.start();
        client.increment("MetricName");
        waitBeforeVerify();
        verify(socket, times(1)).connect();
        verify(socket, times(1)).send("MetricName:1|c");
    }


    @Test
    public void testIncrementWithNoConnection() throws Exception {
        client = new SimpleStatsDClient(socket, executorService, ErrorMode.MODERATE);
        doThrow(StatsDSocketConnectException.class).when(socket).connect();
        client.start();
        client.increment("MetricName");
        waitBeforeVerify();
        verify(socket, times(1)).connect();
        verify(socket, times(0)).send("MetricName:1|c");
    }

    @Test
    public void testIncrementValue() throws Exception {
        client.start();
        client.increment("MetricName", 4);
        waitBeforeVerify();
        verify(socket, times(1)).connect();
        verify(socket, times(1)).send("MetricName:4|c");
    }

    @Test
    public void testGauge() throws Exception {
        client.start();
        client.gauge("MetricName", 4);
        waitBeforeVerify();
        verify(socket, times(1)).connect();
        verify(socket, times(1)).send("MetricName:4|g");
    }

    @Test
    public void testGaugeString() throws Exception {
        client.start();
        client.gauge("MetricName", "ab");
        waitBeforeVerify();
        verify(socket, times(1)).connect();
        verify(socket, times(1)).send("MetricName:ab|g");
    }

    @Test
    public void testTime() throws Exception {
        client.start();
        client.time("MetricName", 4);
        waitBeforeVerify();

        verify(socket, times(1)).connect();
        verify(socket, times(1)).send("MetricName:4|ms");
    }

    /**
     * Method relies on {@link Executors#newSingleThreadExecutor()} in its work, supposing next task completion after
     * test task guarantee completion of test task. (Since the executor is single threaded. Change of executor for tests
     * might break this logic.
     * @throws InterruptedException
     * @throws java.util.concurrent.ExecutionException
     */
    private void waitBeforeVerify() throws InterruptedException, java.util.concurrent.ExecutionException {
        executorService.submit(() -> {
            //for making sure its done;
        }).get();
    }
}