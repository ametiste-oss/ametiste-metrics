package org.ametiste.metrics.experimental.measurement;

/**
 *
 * @since
 */
public class TimeMeasurement {

    private final MeasureDevice measureDevice;

    public TimeMeasurement(MeasureDevice measureDevice) {
        this.measureDevice = measureDevice;
    }

    public void measureTimeOf(String name, Runnable action) {

        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("name must be not null nor empty");
        }

        if (action == null) {
            throw new IllegalArgumentException("action must be not null");
        }

        long startTime = System.currentTimeMillis();

        try {
            action.run();
        } finally {
            long endTime = System.currentTimeMillis();
            measureDevice.writeMeasure(name, (int) (endTime - startTime));
        }
    }

}
