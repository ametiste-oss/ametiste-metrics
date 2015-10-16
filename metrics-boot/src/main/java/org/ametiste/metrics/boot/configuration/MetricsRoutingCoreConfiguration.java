package org.ametiste.metrics.boot.configuration;

import org.ametiste.metrics.MetricsAggregator;
import org.ametiste.metrics.NullMetricsAggregator;
import org.ametiste.metrics.boot.configuration.routing.Route;
import org.ametiste.metrics.boot.configuration.routing.RouteConfiguration;
import org.ametiste.metrics.router.AggregatorsRouter;
import org.ametiste.metrics.router.MappingAggregatorsRouter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.util.*;

@Configuration
public class MetricsRoutingCoreConfiguration {

    @Autowired
    @CoreAggregator
    private List<MetricsAggregator> aggregators;

    @Autowired
    private RouteConfiguration metricsRouting;

    @Autowired
    @CoreAggreagationRouting
    private List<Route> aggregatorRoutes;

    /**
     * <p>
     *  Creates default route for core aggregators, this route has lowest precedence
     *  and would be applied last in the list of all defined routes.
     * </p>
     *
     * @return default route for core aggregators
     */
    @Bean
    @CoreAggreagationRouting
    @Order(Ordered.LOWEST_PRECEDENCE)
    public Route defaultCoreRouting() {
        return Route.create(MappingAggregatorsRouter.DEFAULT_ROUTE_NAME, aggregators);
    }

    @Bean
    public RouteConfiguration metricRoutingMap() {
        return new RouteConfiguration(aggregatorRoutes);
    }

    @Bean
    @CoreAggregator
    public MetricsAggregator nullAggregator() {
        return  new NullMetricsAggregator();
    }

    @Bean
    public AggregatorsRouter aggregatorsRouter() {
        return new MappingAggregatorsRouter(metricsRouting.asMap());
    }

}
