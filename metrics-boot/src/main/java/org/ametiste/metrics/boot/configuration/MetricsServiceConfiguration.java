package org.ametiste.metrics.boot.configuration;

import org.ametiste.metrics.AggregatingMetricsService;
import org.ametiste.metrics.MetricsAggregator;
import org.ametiste.metrics.MetricsService;
import org.ametiste.metrics.NullMetricsAggregator;
import org.ametiste.metrics.aop.IdentifierResolver;
import org.ametiste.metrics.container.MapContainer;
import org.ametiste.metrics.resolver.PlainMetricsIdentifierResolver;
import org.ametiste.metrics.router.AggregatorsRouter;
import org.ametiste.metrics.router.MappingAggregatorsRouter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.expression.spel.standard.SpelExpressionParser;

@Configuration
@Import({JmxConfiguration.class, StatsDConfiguration.class, MetricsRoutingConfiguration.class})
@EnableConfigurationProperties(MetricsProperties.class)
public class MetricsServiceConfiguration {

    @Autowired
    private MetricsProperties properties;

    @Autowired
    private MapContainer metricRoutingMap;

    @Bean
    public AggregatorsRouter aggregatorsRouter() {
        return new MappingAggregatorsRouter(metricRoutingMap);
    }

    @Bean
    @Qualifier("metricsService")
    public MetricsService metricsService() {
       return new AggregatingMetricsService(
               aggregatorsRouter(),
               new PlainMetricsIdentifierResolver(),
               properties.getPrefix());
    }

    @Bean
    public SpelExpressionParser spelParser() {
        return new SpelExpressionParser();
    }

    @Bean
    public IdentifierResolver nameResolver() {
        return new IdentifierResolver(spelParser());
    }

    @Bean
    public MetricsAggregator nullAggregator() {
        return  new NullMetricsAggregator();
    }

}
