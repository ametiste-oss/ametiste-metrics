package org.ametiste.metrics.jmx;

import com.codahale.metrics.Gauge;

/**
 * Created by ametiste on 12/28/15.
 */
public class UpdateableGauge implements Gauge<Integer> {

    private int value;

    public UpdateableGauge(int value) {
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    public void update(int value) {
        this.value = value;
    }
}
