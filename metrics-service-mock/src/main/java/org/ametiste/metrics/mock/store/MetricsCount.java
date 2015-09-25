package org.ametiste.metrics.mock.store;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daria on 24.07.2014.
 */
public class MetricsCount {

    private int count;
    private List<Long> values;

    public MetricsCount() {
        count = 0;
        values = new ArrayList<>();
    }

    public void addValue(Long value) {
        count++;
        values.add(value);
    }

    public List<Long> getValues() {
        return this.values;
    }

    public int getCount() {
        return this.count;
    }
}
