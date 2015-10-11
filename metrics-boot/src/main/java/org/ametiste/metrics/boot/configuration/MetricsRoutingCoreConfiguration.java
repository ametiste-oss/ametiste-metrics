package org.ametiste.metrics.boot.configuration;

import org.ametiste.metrics.MetricsAggregator;
import org.ametiste.metrics.NullMetricsAggregator;
import org.ametiste.metrics.container.ListContainer;
import org.ametiste.metrics.container.MapContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class MetricsRoutingCoreConfiguration {

    @Autowired
    @CoreAggregator
    private List<MetricsAggregator> aggregators;

    @Bean
    public MapContainer metricRoutingMap() {
        Map<String, ListContainer> map = new HashMap<>();
        map.put("__default", aggregatorsContainer());
        return new MapContainer(map);
    }

    @Bean
    public ListContainer aggregatorsContainer() {
        return new ListContainer(aggregators);
    }

    @Bean
    @CoreAggregator
    public MetricsAggregator nullAggregator() {
        return  new NullMetricsAggregator();
    }

}
