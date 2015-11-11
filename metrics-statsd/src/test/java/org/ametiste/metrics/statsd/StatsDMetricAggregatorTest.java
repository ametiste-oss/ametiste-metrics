package org.ametiste.metrics.statsd;


import org.ametiste.metrics.statsd.client.StatsDClient;
import org.junit.Ignore;
import org.junit.Test;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class StatsDMetricAggregatorTest {

    @Test
    @Ignore
    public void testCallIncrementOnRandomValue() throws Exception {

        StatsDClient statsDClient = mock(StatsDClient.class);
        StatsDMetricAggregator statsDMetricAggregator = new StatsDMetricAggregator(statsDClient);

        int inc = 123;
        statsDMetricAggregator.increment("", inc);

        verify(statsDClient).increment(eq(""), eq(123));
    }
}
