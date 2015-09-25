package org.ametiste.metrics.container;

import org.ametiste.metrics.MetricsAggregator;

import java.util.List;

/**
 * Serves as a list wrapper. Helps with beans creation in mixed xml/java context. Might be just a temporary hack
 * @since 0.1.0
 * @author ametiste
 */
public class ListContainer {
//TODO make generic maybe

    private List<MetricsAggregator> list;

    public ListContainer(List<MetricsAggregator> list) {

        this.list = list;
    }

    public List<MetricsAggregator> loadList() {
        return list;
    }
}
