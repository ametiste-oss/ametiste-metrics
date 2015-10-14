package org.ametiste.metrics.boot.configuration.container;

import org.ametiste.metrics.MetricsAggregator;

import java.util.List;
import java.util.Map;

/**
 * Map wrapper, helps with beans creation in mixed xml/java context. Might be just a temporary hack
 * @since 0.1.0
 * @author ametiste
 */
public class MapContainer {

    private Map<String, ListContainer> map;

    public MapContainer(Map<String, ListContainer> map) {
        this.map = map;
    }

    public Map<String, ListContainer> loadMap() {
        return map;
    }
}
