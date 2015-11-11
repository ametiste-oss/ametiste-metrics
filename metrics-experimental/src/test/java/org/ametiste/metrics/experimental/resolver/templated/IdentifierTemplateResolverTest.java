package org.ametiste.metrics.experimental.resolver.templated;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 *
 * @since
 */
public class IdentifierTemplateResolverTest {

    private final static String METRIC_ID = "metricId";
    private IdentifierTemplateResolver templateResolver;
    @Mock
    private VariableBind corePrefixBind;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        templateResolver = new IdentifierTemplateResolver(
                Arrays.asList(corePrefixBind)
        );
    }

    @Test
    public void testResolveMetricId() throws Exception {

        when(corePrefixBind.bindVariable())
                .thenReturn(new BindedVariable("corePrefix", "mockPrefix"));

        final String resolvedName = templateResolver.resolveMetricId(METRIC_ID);

        assertEquals("mockPrefix.metricId", resolvedName);
    }

}