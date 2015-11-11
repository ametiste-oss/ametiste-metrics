package org.ametiste.metrics.mock;

import org.ametiste.metrics.mock.store.MetricsType;
import org.ametiste.metrics.mock.store.MockMetricsContainer;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Verify chain with methods to verify specific conditions of metric registration - type of metric (count, time),
 * number of times registered and values of registered metrics.
 * Created on 24.07.2014.
 *
 * @author ametiste
 * @since 0.1.0
 */
public class MockMetricsVerifier {

    private final String name;
    private MockMetricsContainer container;

    public MockMetricsVerifier(String name, MockMetricsContainer container) {

        this.container = container;
        this.name = name;

    }

    /**
     * Verifies metric with requested name was registered without type and numbers check
     *
     * @return verify chain
     */
    public MockMetricsVerifier registered() {
        assertTrue("Metric with name '" + name + "' was expected to be registered in service, but wasnt", container.hasValueWithName(name));
        return this;
    }

    /**
     * Verifies metric with requested name was not registered. Ends chain of verifies.
     */
    public void notRegistered() {
        assertFalse("Metric with name '" + name + "' not expected to be registered by service, but actually was", container.hasValueWithName(name));
    }

    /**
     * Verifies metric with requested name was registered as incremental one, without check number
     * of calls and values. Can be used in serial call after {@link MockMetricsVerifier#registered()},
     * to provide more clear failure trace, however this method success guarantees success of mentioned one
     *
     * @return verify chain
     */
    public MockMetricsVerifier increment() {
        assertTrue("Metric with name '" + name + "' was expected to be registered as incremental, but wasnt", container.hasValueWithTypeAndName(MetricsType.INCR_VALUE, name));
        return this;
    }

    /**
     * Verifies metric with requested name was registered as incremental one, with definite
     * number of calls, not checking values registered. Can be used in serial call after
     * {@link MockMetricsVerifier#registered()}, {@link MockMetricsVerifier#increment()}
     * to provide more clear failure trace, however this method success guarantees success of mentioned ones
     *
     * @return verify chain
     */
    public MockMetricsVerifier increment(int times) {
        assertTrue("Metric with name '" + name + "' was expected to be registered as incremental " + times + " times, but actually was registered "
                        + container.getCountForValue(MetricsType.INCR_VALUE, name) + " times",
                container.hasValueWithTypeAndNameAndCount(MetricsType.INCR_VALUE, name, times));
        return this;
    }

    /**
     * Verifies metric with requested name was registered as incremental one, with definite
     * number of calls, with list of values provided. Can be used in serial call after
     * {@link MockMetricsVerifier#registered()}, {@link MockMetricsVerifier#increment()}
     * and {@link MockMetricsVerifier#increment(int)} to provide more clear failure trace,
     * however this method success guarantees success of mentioned ones.
     * Ends verify chain
     */
    public void increment(List<Long> values) {
        assertTrue("Metric with name '" + name + "'  was expected to be registered as incremental with values " + values + " , but actually was registered with values "
                + container.getValuesForValue(MetricsType.INCR_VALUE, name),
                container.hasValueWithTypeAndNameAndValues(MetricsType.INCR_VALUE, name, values));
    }

    /**
     * Verifies metric with requested name was registered as gauge, without check number
     * of calls and values. Can be used in serial call after {@link MockMetricsVerifier#registered()},
     * to provide more clear failure trace, however this method success guarantees success of mentioned one
     *
     * @return verify chain
     */
    public MockMetricsVerifier gauge() {
        assertTrue("Metric with name '" + name + "' was expected to be registered as gauge, but wasnt",
                container.hasValueWithTypeAndName(MetricsType.GAUGE, name));
        return this;
    }

    /**
     * Verifies metric with requested name was registered as gauge, with definite
     * number of calls, not checking values registered. Can be used in serial call after
     * {@link MockMetricsVerifier#registered()}, {@link MockMetricsVerifier#gauge()}
     * to provide more clear failure trace, however this method success guarantees success of mentioned ones
     *
     * @return verify chain
     */
    public MockMetricsVerifier gauge(int times) {
        assertTrue("Metric with name '" + name + "' was expected to be registered as gauge " + times + " times, but actually was registered "
                + container.getCountForValue(MetricsType.GAUGE, name) + " times",
                container.hasValueWithTypeAndNameAndCount(MetricsType.GAUGE, name, times));
        return this;
    }

    /**
     * Verifies metric with requested name was registered as gauge with definite
     * number of calls, with list of values provided. Can be used in serial call after
     * {@link MockMetricsVerifier#registered()}, {@link MockMetricsVerifier#gauge()}
     * and {@link MockMetricsVerifier#gauge(int)}  to provide more clear failure trace,
     * however this method success guarantees success of mentioned ones.
     * Ends verify chain
     */
    public void gauge(List<Long> values) {
        assertTrue("Metric with name '" + name + "' was expected to be registered as gauge with values " + values + " , but actually was registered with values "
                + container.getValuesForValue(MetricsType.GAUGE, name),
                container.hasValueWithTypeAndNameAndValues(MetricsType.GAUGE, name, values));
    }

    /**
     * Verifies metric with requested name was registered as time update, without check number
     * of calls and values. Can be used in serial call after {@link MockMetricsVerifier#registered()},
     * to provide more clear failure trace, however this method success guarantees success of mentioned one
     *
     * @return verify chain
     */
    public MockMetricsVerifier event() {
        assertTrue("Metric with name '" + name + "'  was expected to be registered as timer or chronable event value, but wasnt", container.hasValueWithTypeAndName(MetricsType.TIME, name));
        return this;
    }

    /**
     * Verifies metric with requested name was registered as time update, with definite
     * number of calls, not checking values registered. Can be used in serial call after
     * {@link MockMetricsVerifier#registered()}, {@link MockMetricsVerifier#event()}
     * to provide more clear failure trace, however this method success guarantees success of mentioned ones
     *
     * @return verify chain
     */
    public MockMetricsVerifier event(int times) {
        assertTrue("Metric with name '" + name + "' was expected to be registered as timer or chronable event value " + times + " times, but actually was registered "
                + container.getCountForValue(MetricsType.TIME, name) + " times", container.hasValueWithTypeAndNameAndCount(MetricsType.TIME, name, times));
        return this;
    }

    /**
     * Verifies metric with requested name was registered as time update, with definite
     * number of calls, with list of values provided. (Useful for checking of events registration
     * those values are well known) Can be used in serial call after
     * {@link MockMetricsVerifier#registered()}, {@link MockMetricsVerifier#event()}
     * and {@link MockMetricsVerifier#event(int)} to provide more clear failure trace,
     * however this method success guarantees success of mentioned ones.
     * Ends verify chain
     */
    public void event(List<Long> values) {

        assertTrue("Metric with name '" + name + "' was expected to be registered as timer or chronable event value with values " + values + " , but actually was registered with values "
                + container.getValuesForValue(MetricsType.TIME, name), container.hasValueWithTypeAndNameAndValues(MetricsType.TIME, name, values));
    }

}
