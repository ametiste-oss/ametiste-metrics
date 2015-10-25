package org.ametiste.metrics.mock.store;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Temporary metrics gathering storage with helper methods for easier verification
 * Created on 24.07.2014.
 *
 * @author ametiste
 * @since 0.1.0
 */
public class MockMetricsContainer {

    private Map<String, MetricsCount> gaugeIncMap;
    private Map<String, MetricsCount> rangeIncMap;
    private Map<String, MetricsCount> timeMap;
    private Map<MetricsType, Map<String, MetricsCount>> container;

    public MockMetricsContainer() {
        gaugeIncMap = new HashMap<>();
        rangeIncMap = new HashMap<>();
        timeMap = new HashMap<>();
        container = new HashMap<>();
        container.put(MetricsType.GAUGE, gaugeIncMap);
        container.put(MetricsType.INCR_VALUE, rangeIncMap);
        container.put(MetricsType.TIME, timeMap);

    }

    public void addValue(MetricsType type, String metricName, long value) {

        this.insertData(container.get(type), metricName, value);
    }

    private void insertData(Map<String, MetricsCount> map, String targetName, long value) {
        if (map.containsKey(targetName)) {
            map.get(targetName).addValue(value);
        } else {
            MetricsCount c = new MetricsCount();
            c.addValue(value);
            map.put(targetName, c);
        }
    }

    public boolean hasValueWithName(String name) {
        return gaugeIncMap.containsKey(name) || rangeIncMap.containsKey(name) || timeMap.containsKey(name);
    }

    public boolean hasValueWithTypeAndName(MetricsType type, String name) {
        return container.get(type).containsKey(name);
    }

    public boolean hasValueWithTypeAndNameAndCount(MetricsType type, String name, int times) {
        return container.get(type).containsKey(name) && container.get(type).get(name).getCount() == times;
    }


    public boolean hasValueWithTypeAndNameAndValues(MetricsType type, String name, List<Long> values) {
        return container.get(type).containsKey(name) && values.equals(container.get(type).get(name).getValues());
    }

    public int getCountForValue(MetricsType type, String name) {
        if (!container.get(type).containsKey(name)) {
            throw new IllegalArgumentException("No metric with this name is registered for this type");
        }
        return container.get(type).get(name).getCount();
    }

    public List<Long> getValuesForValue(MetricsType type, String name) {
        if (!container.get(type).containsKey(name)) {
            throw new IllegalArgumentException("No metric with this name is registered for this type");
        }
        return container.get(type).get(name).getValues();
    }

    public void clear() {
        gaugeIncMap.clear();
        rangeIncMap.clear();
        timeMap.clear();
    }
}
