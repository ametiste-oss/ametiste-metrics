package org.ametiste.metrics.experimental.streams;

import org.ametiste.metrics.MetricsAggregator;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

/**
 *
 * @since
 */
public class AggregatorStreamTest {

    private static final String METRIC_ID = "METRIC_ID";
    private static final int METRIC_VALUE = 12345;

    private AggregatorStream stream;

    @Mock
    private MetricsAggregator metricsAggregator;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        stream = new AggregatorStream(metricsAggregator);
    }

    @Test
    public void testIncrement() throws Exception {
        stream.increment(METRIC_ID, METRIC_VALUE);
        verify(metricsAggregator).increment(METRIC_ID, METRIC_VALUE);
    }

    @Test
    public void testEvent() throws Exception {
        stream.event(METRIC_ID, METRIC_VALUE);
        verify(metricsAggregator).event(METRIC_ID, METRIC_VALUE);
    }

    @Test
    public void testGauge() throws Exception {
        stream.gauge(METRIC_ID, METRIC_VALUE);
        verify(metricsAggregator).gauge(METRIC_ID, METRIC_VALUE);
    }
}