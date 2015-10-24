package org.ametiste.metrics.mock;

import org.ametiste.metrics.MetricsService;
import org.ametiste.metrics.mock.store.MetricsType;
import org.ametiste.metrics.mock.store.MockMetricsContainer;

/**
 * Mock metric service for test needs. Provides both independence from external aggregators and herefore
 * external calls, and ability to verify expected metrics registration.
 * Is recommended for use in test environment.
 *
 * @author ametiste
 * @since 0.1.0
 */
public class MockMetricsService implements MetricsService {

    private boolean verifyChainStarted = false;

    private MockMetricsContainer container = new MockMetricsContainer();

    /**
     * If this method is used after verify chain started, IllegalArgumentException is thrown
     * {@inheritDoc}
     */
    @Override
    public void increment(String targetName) {
        checkIfAlreadyVerified();
        container.addValue(MetricsType.INCR, targetName, 1L);

    }

    private void checkIfAlreadyVerified() {
        if (verifyChainStarted) {
            throw new IllegalArgumentException("After verify chain is started, service cant be used as usual anymore, maybe you need 'resetData()' method call to reset service work");
        }
    }

    /**
     * If this method is used after verify chain started, IllegalArgumentException is thrown
     * {@inheritDoc}
     */
    @Override
    public void increment(String targetName, int incrementValue) {
        checkIfAlreadyVerified();
        container.addValue(MetricsType.INCR_VALUE, targetName, incrementValue);
    }

    /**
     * If this method is used after verify chain started, IllegalArgumentException is thrown
     * {@inheritDoc}
     */
    @Override
    public void createEvent(String name, long startTime, long endTime) {
        checkIfAlreadyVerified();
        long incrementValue = endTime - startTime;

        container.addValue(MetricsType.TIME, name, incrementValue);
    }

    /**
     * If this method is used after verify chain started, IllegalArgumentException is thrown
     * {@inheritDoc}
     */
    @Override
    public void createEvent(String metricId, int eventValue) {
        checkIfAlreadyVerified();
        container.addValue(MetricsType.TIME, metricId, eventValue);
    }

    /**
     * Starts chain of verify calls. After verify is started, no further service call can be made.
     * If mock is used as service for registration after verify, IllegalArgumentException is thrown
     *
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
