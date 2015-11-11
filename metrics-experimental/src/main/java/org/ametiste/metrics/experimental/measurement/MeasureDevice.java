package org.ametiste.metrics.experimental.measurement;

public interface MeasureDevice {

    void writeMeasure(String measurementName, int value);

}
