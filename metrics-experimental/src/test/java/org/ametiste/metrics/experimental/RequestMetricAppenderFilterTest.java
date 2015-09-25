package org.ametiste.metrics.experimental;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 *
 * @since
 */
public class RequestMetricAppenderFilterTest {

    private @Mock MetricProvider metricProvider;

    private @Mock HttpServletRequest request;

    private @Mock HttpServletResponse response;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testDoFilter() throws Exception {

        final RequestMetricAppender filter = new RequestMetricAppender(metricProvider);

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                ((MetricConsumer)invocation.getArguments()[0]).consume("test-1", "metric-1");
                ((MetricConsumer)invocation.getArguments()[0]).consume("test-2", "metric-2");
                return null;
            }
        }).when(metricProvider).provideMetricValues(any(MetricConsumer.class));

        filter.postHandle(request, response, null, null);

        verify(response).addHeader("X-Dph-Metric-test-1", "metric-1");
        verify(response).addHeader("X-Dph-Metric-test-2", "metric-2");

    }

}
