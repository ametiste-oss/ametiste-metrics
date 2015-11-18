package org.ametiste.metrics.experimental.streams;

import org.ametiste.metrics.MetricsService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

/**
 *
 * @since
 */
public class MetricsServiceStreamTest {

    private final static String METRIC_ID = "METRIC_ID";

    private final static int METRIC_VALUE = 102;

    @Mock
    private MetricsService upstreamMetricsService;
    private MetricsServiceStream stream;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        stream = new MetricsServiceStream(upstreamMetricsService);
    }

    @Test
    public void testIncrement() throws Exception {
        stream.increment(METRIC_ID, METRIC_VALUE);
        verify(upstreamMetricsService).increment(METRIC_ID, METRIC_VALUE);
    }

    @Test
    public void testEvent() throws Exception {
        stream.event(METRIC_ID, METRIC_VALUE);
        verify(upstreamMetricsService).createEvent(METRIC_ID, METRIC_VALUE);
    }
}