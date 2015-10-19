package org.ametiste.metrics;

import org.ametiste.metrics.resolver.MetricsIdentifierResolver;
import org.ametiste.metrics.router.AggregatorsRouter;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;


public class AggregatingMetricsServiceTest {

    private final AggregatorsRouter aggregatorsRouter = mock(AggregatorsRouter.class);
    private final MetricsIdentifierResolver resolver = mock(MetricsIdentifierResolver.class);
    private AggregatingMetricsService aggregatingMetricsService;

    @Before
    public void setUp() throws Exception {
        aggregatingMetricsService = new AggregatingMetricsService(aggregatorsRouter, resolver, "");
    }

    @Test
    public void testIncrementOnRandomValue() throws Exception {

        MetricsAggregator aggregator = mock(MetricsAggregator.class);
        MetricsAggregator aggregator1 = mock(MetricsAggregator.class);
        List<MetricsAggregator> aggregatorsList = new ArrayList<>(Arrays.asList(
                aggregator, aggregator1
        ));

        when(aggregatorsRouter.getAggregatorsForMetric(anyString())).thenReturn(aggregatorsList);

        int incrementValue = 3;
        aggregatingMetricsService.increment("", incrementValue);

        verify(aggregator).increment(anyString(), eq(incrementValue));
        verify(aggregator1).increment(anyString(), eq(incrementValue));
    }
}
