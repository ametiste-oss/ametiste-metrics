package org.ametiste.metrics.experimental.measurement;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

/**
 *
 * @since
 */
public class TimeMeasurementTest {

    @Mock
    private MeasureDevice measureDevice;

    private TimeMeasurement timeMeasurement;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        timeMeasurement = new TimeMeasurement(measureDevice);
    }

    @Test
    public void testMeasureTimeOf() throws Exception {
        timeMeasurement.measureTimeOf("test.time", () -> {
        });
        verify(measureDevice).writeMeasure(eq("test.time"), anyInt());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMeasureTimeOfNull() throws Exception {
        timeMeasurement.measureTimeOf("test.time", null);
    }

    @Test
    public void testMeasureTimeOfErrorProne() throws Exception {
        try {
            timeMeasurement.measureTimeOf("test.time", () -> {
                throw new RuntimeException();
            });
        } catch (RuntimeException e) {
        } finally {
            verify(measureDevice).writeMeasure(eq("test.time"), anyInt());
        }
    }

}