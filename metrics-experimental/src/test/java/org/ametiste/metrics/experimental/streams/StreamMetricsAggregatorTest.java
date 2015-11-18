package org.ametiste.metrics.experimental.streams;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import static org.mockito.Mockito.verify;

/**
 *
 * @since
 */
public class StreamMetricsAggregatorTest {

    private final static String METRIC_ID = "METRIC_ID";

    private final static int METRIC_VALUE = 102;

    @Mock
    private MetricsStream upstreamA;

    @Mock
    private MetricsStream upstreamB;

    private StreamMetricsAggregator metricsAggregator;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        metricsAggregator = new StreamMetricsAggregator(Arrays.asList(upstreamA, upstreamB));
    }

    @Test
    public void testGauge() throws Exception {
        metricsAggregator.gauge(METRIC_ID, METRIC_VALUE);
        verify(upstreamA).gauge(METRIC_ID, METRIC_VALUE);
        verify(upstreamB).gauge(METRIC_ID, METRIC_VALUE);
    }

    @Test
    public void testEvent() throws Exception {
        metricsAggregator.event(METRIC_ID, METRIC_VALUE);
        verify(upstreamA).event(METRIC_ID, METRIC_VALUE);
        verify(upstreamB).event(METRIC_ID, METRIC_VALUE);
    }

    @Test
    public void testIncrement() throws Exception {
        metricsAggregator.increment(METRIC_ID, METRIC_VALUE);
        verify(upstreamA).increment(METRIC_ID, METRIC_VALUE);
        verify(upstreamB).increment(METRIC_ID, METRIC_VALUE);
    }
}