package org.ametiste.metrics.experimental.activator;

import org.ametiste.metrics.resolver.MetricsIdentifierResolver;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 *
 * @since
 */
public class ResolverActivatorTest {

    private static final String UNRESOLVED_NAME = "METIC_NAME";

    private static final String RESOLVED_ID = "METIC_ID";

    private ResolverActivator resolverActivator;

    @Mock
    private MetricsIdentifierResolver metricsIdentifierResolver;

    @Mock
    private ActivationCondition activationCondition;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        resolverActivator = new ResolverActivator(metricsIdentifierResolver, activationCondition);
    }

    @Test
    public void testResolveMetricIdActivated() throws Exception {
        when(activationCondition.checkActivation()).thenReturn(true);
        when(metricsIdentifierResolver.resolveMetricId(UNRESOLVED_NAME)).thenReturn(RESOLVED_ID);

        final String actualMetricId = resolverActivator.resolveMetricId(UNRESOLVED_NAME);

        verify(metricsIdentifierResolver).resolveMetricId(UNRESOLVED_NAME);
        assertEquals(RESOLVED_ID, actualMetricId);
    }

    @Test
    public void testResolveMetricIdNotActivated() throws Exception {
        when(activationCondition.checkActivation()).thenReturn(false);

        final String actualMetricId = resolverActivator.resolveMetricId(UNRESOLVED_NAME);

        verify(metricsIdentifierResolver, times(0)).resolveMetricId(UNRESOLVED_NAME);
        // NOTE: if resolver is not active, unresolved name should be returned
        assertEquals(UNRESOLVED_NAME, actualMetricId);
    }

}