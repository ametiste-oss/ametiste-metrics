package org.ametiste.metrics.boot.configuration;

import org.ametiste.metrics.MetricsAggregator;
import org.ametiste.metrics.boot.configuration.routing.Route;
import org.ametiste.metrics.router.MappingAggregatorsRouter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.util.List;

/**
 *
 * @since
 */
@Configuration
@AutoConfigureBefore(MetricsRoutingCoreConfiguration.class)
public class DefaultRoutingConfiguration {

    @Autowired
    @CoreAggregator
    private List<MetricsAggregator> aggregators;

    /**
     * <p>
     *  Creates default route for core aggregators, this route has lowest precedence
     *  and would be applied last in the list of all defined routes.
     * </p>
     *
     * @return default route for core aggregators
     */
    @Bean
    @CoreAggreagatorRoute
    @Order(Ordered.LOWEST_PRECEDENCE)
    public Route defaultCoreRouting() {
        return Route.create(MappingAggregatorsRouter.DEFAULT_ROUTE_NAME, aggregators);
    }

}
