package org.ametiste.metrics.experimental.measurement;

/**
 *
 * @since
 */
public class TimeMeasurement {

    private final MeasureDevice measureDevice;

    public interface MeasureDevice {
        void writeMeasure(String measurementName, int value);
    }

    public interface Action {
        void peroform();
    }

    public TimeMeasurement(MeasureDevice measureDevice) {
        this.measureDevice = measureDevice;
    }

    public void measureTimeOf(String name, Action action) {
        long startTime = System.currentTimeMillis();
        try {
            action.peroform();
        } finally {
            long endTime = System.currentTimeMillis();
            measureDevice.writeMeasure(name, (int) (endTime - startTime));
        }
    }

}
