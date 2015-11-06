package org.ametiste.metrics.experimental.streams;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

/**
 *
 * @since
 */
public class MetaMetricsStreamTest {

    private static final String METRIC_ID = "METRIC_ID";
    private static final int METRIC_VALUE = 12345;

    @Mock
    private MetricsStream upstream;

    @Mock
    private MetricsStream metaStream;

    private MetaMetricsStream metaMetricsStream;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        metaMetricsStream = new MetaMetricsStream(upstream, metaStream);
    }

    @Test
    public void testIncrement() throws Exception {
        metaMetricsStream.increment(METRIC_ID, METRIC_VALUE);
        verify(upstream).increment(METRIC_ID, METRIC_VALUE);
        verify(metaStream).event(eq(MetaMetricsStream.META_INCREMENT_METRIC), anyInt());
    }

    @Test
    public void testEvent() throws Exception {
        metaMetricsStream.event(METRIC_ID, METRIC_VALUE);
        verify(upstream).event(METRIC_ID, METRIC_VALUE);
        verify(metaStream).event(eq(MetaMetricsStream.META_EVENT_METRIC), anyInt());
    }

    @Test
    public void testGauge() throws Exception {
        metaMetricsStream.gauge(METRIC_ID, METRIC_VALUE);
        verify(upstream).gauge(METRIC_ID, METRIC_VALUE);
        verify(metaStream).event(eq(MetaMetricsStream.META_GAUGE_METRIC), anyInt());
    }
}