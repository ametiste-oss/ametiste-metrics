package org.ametiste.metrics.boot.configuration;

import org.ametiste.metrics.AggregatingMetricsService;
import org.ametiste.metrics.MetricsAggregator;
import org.ametiste.metrics.NullMetricsAggregator;
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

    // NOTE: bean name used as map key during autowire process, so default routing name used
    @Bean(name = MappingAggregatorsRouter.DEFAULT_ROUTE_NAME)
    @CoreAggreagationRouting
    public List<MetricsAggregator> metricRoutingMap() {
        return aggregators;
    }

    @Bean
    @CoreAggregator
    public MetricsAggregator nullAggregator() {
        return  new NullMetricsAggregator();
    }

}