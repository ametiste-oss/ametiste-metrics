package org.ametiste.metrics.boot.configuration;

import org.ametiste.metrics.MetricsAggregator;
import org.ametiste.metrics.boot.configuration.routing.Route;
import org.ametiste.metrics.boot.configuration.routing.RouteConfiguration;
import org.ametiste.metrics.router.AggregatorsRouter;
import org.ametiste.metrics.router.MappingAggregatorsRouter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;

@Configuration
public class MetricsRoutingCoreConfiguration {

    @Autowired
    @CoreAggregator
    private List<MetricsAggregator> aggregators;

    @Autowired
    private List<Route> routes;

    @Bean
    public RouteConfiguration routeConfiguration() {
        return new RouteConfiguration(routes);
    }

    @Bean
    public AggregatorsRouter aggregatorsRouter() {
        return new MappingAggregatorsRouter(routeConfiguration().asMap());
    }

}
