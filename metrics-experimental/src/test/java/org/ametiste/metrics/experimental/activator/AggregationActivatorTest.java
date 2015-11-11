package org.ametiste.metrics.experimental.activator;

import org.ametiste.metrics.MetricsAggregator;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

/**
 *
 * @since
 */
public class AggregationActivatorTest {

    private final static String METRIC_ID = "metricId";
    private final static int METRIC_VALUE = 123;
    private AggregationActivator activator;

    @Mock
    private MetricsAggregator mockUpstream;

    @Mock
    private ActivationCondition mockCondition;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        activator = new AggregationActivator(mockUpstream, mockCondition);
    }

    @Test
    public void testGaugeActivated() throws Exception {
        when(mockCondition.checkActivation()).thenReturn(true);
        activator.gauge(METRIC_ID, METRIC_VALUE);
        verify(mockUpstream).gauge(METRIC_ID, METRIC_VALUE);
    }

    @Test
    public void testGaugeNotActivated() throws Exception {
        when(mockCondition.checkActivation()).thenReturn(false);
        activator.gauge(METRIC_ID, METRIC_VALUE);
        verify(mockUpstream, times(0)).gauge(METRIC_ID, METRIC_VALUE);
    }

    @Test
    public void testEventActivated() throws Exception {
        when(mockCondition.checkActivation()).thenReturn(true);
        activator.event(METRIC_ID, METRIC_VALUE);
        verify(mockUpstream).event(METRIC_ID, METRIC_VALUE);
    }

    @Test
    public void testEventNotActivated() throws Exception {
        when(mockCondition.checkActivation()).thenReturn(false);
        activator.event(METRIC_ID, METRIC_VALUE);
        verify(mockUpstream, times(0)).event(METRIC_ID, METRIC_VALUE);
    }

    @Test
    public void testIncrementActivated() throws Exception {
        when(mockCondition.checkActivation()).thenReturn(true);
        activator.increment(METRIC_ID, METRIC_VALUE);
        verify(mockUpstream).increment(METRIC_ID, METRIC_VALUE);
    }

    @Test
    public void testIncrementNotActivated() throws Exception {
        when(mockCondition.checkActivation()).thenReturn(false);
        activator.increment(METRIC_ID, METRIC_VALUE);
        verify(mockUpstream, times(0)).increment(METRIC_ID, METRIC_VALUE);
    }

}