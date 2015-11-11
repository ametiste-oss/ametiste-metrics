package org.ametiste.metrics.experimental.boot;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 *
 * @since
 */
public class BootMetricServiceAggregatorTest {

    private static final String METRIC_ID = "METIC_ID";

    private static final int METRIC_VALUE = 1203;

    @Mock
    private GaugeService gaugeService;

    @Mock
    private CounterService counterService;

    private BootMetricServiceAggregator bootMetricServiceAggregator;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        bootMetricServiceAggregator = new BootMetricServiceAggregator(counterService, gaugeService);
    }

    @Test
    public void testGauge() throws Exception {
        bootMetricServiceAggregator.gauge(METRIC_ID, METRIC_VALUE);

        verify(gaugeService).submit(METRIC_ID, METRIC_VALUE);
        verify(counterService, times(0)).increment(anyString());
    }

    @Test
    public void testEvent() throws Exception {
        bootMetricServiceAggregator.event(METRIC_ID, METRIC_VALUE);

        // NOTE: #event() method is not implemented by spring-boot aggreagtor, so nothing should happen
        verify(gaugeService, times(0)).submit(anyString(), anyInt());
        verify(counterService, times(0)).increment(anyString());
    }

    @Test
    public void testIncrement() throws Exception {
        bootMetricServiceAggregator.increment(METRIC_ID, 1);

        verify(counterService, times(1)).increment(METRIC_ID);
    }

    @Test
    public void testMultipleIncrement() throws Exception {
        bootMetricServiceAggregator.increment(METRIC_ID, 5);

        verify(counterService, times(5)).increment(METRIC_ID);
    }

    @Test
    public void testMultipleIncrementOverThreshold() throws Exception {

        bootMetricServiceAggregator.increment(METRIC_ID,
                BootMetricServiceAggregator.INCREMENT_THRESHOLD + 1);

        // NOTE: if increment threshold value is exceeded, upstream increment should not be invoked
        verify(counterService, times(0)).increment(METRIC_ID);
    }
}