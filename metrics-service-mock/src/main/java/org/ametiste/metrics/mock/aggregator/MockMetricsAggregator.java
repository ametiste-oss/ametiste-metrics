package org.ametiste.metrics.mock.aggregator;

import org.ametiste.metrics.mock.store.MetricsType;
import org.ametiste.metrics.mock.store.MockMetricsContainer;
import org.ametiste.metrics.mock.MockMetricsVerifier;
import org.ametiste.metrics.MetricsAggregator;

/**
 * Mock metrics aggregator for test needs. Provides independence from external
 * calls, and ability to verify expected metrics registration.
 * Is recommended for use in test environment when MockService cant be applied but list of aggregators
 * can be replaced instead
 * @author ametiste
 * @since 0.1.0
 */
public class MockMetricsAggregator implements MetricsAggregator {

    boolean verifyChainStarted = false;

    private MockMetricsContainer container = new MockMetricsContainer();

    private void checkIfAlreadyVerified() {
        if(verifyChainStarted) {
            throw new IllegalArgumentException("After verify chain is started, service cant be used as usual anymore, maybe you need 'resetData()' method call to reset service work");
        }
    }

    /**
     * If this method is used after verify chain started, IllegalArgumentException is thrown
     * {@inheritDoc}
     */
    @Override
    public void increment(String metricId) {
        checkIfAlreadyVerified();
        container.addValue(MetricsType.INCR, metricId, 1);
    }

    /**
     * If this method is used after verify chain started, IllegalArgumentException is thrown
     * {@inheritDoc}
     */
    @Override
    public void event(String metricId, int value) {
        checkIfAlreadyVerified();
        container.addValue(MetricsType.TIME, metricId, value);
    }

    /**
     * If this method is used after verify chain started, IllegalArgumentException is thrown
     * {@inheritDoc}
     */
    @Override
    public void increment(String metricId, int inc) {
        checkIfAlreadyVerified();
        container.addValue(MetricsType.INCR_VALUE, metricId, inc);
    }

    /**
     * Starts chain of verify calls. After verify is started, no further service call can be made.
     * If mock is used as service for registration after verify, IllegalArgumentException is thrown
     * @param name metric name to verify
     * @return verify chain for checking specific metrics registration
     */
    public MockMetricsVerifier verify(String name) {
        verifyChainStarted = true;
        return new MockMetricsVerifier(name, container);

    }

    /**
     * Resets verified data and starts metrics gathering. Should be called before or after test methods,
     * between new metrics sets are gathered
     */
    public void resetData() {
        verifyChainStarted = false;
        container.clear();
    }
}
