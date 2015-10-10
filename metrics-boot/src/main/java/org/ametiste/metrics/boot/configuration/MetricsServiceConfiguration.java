package org.ametiste.metrics.boot.configuration;

import org.ametiste.metrics.AggregatingMetricsService;
import org.ametiste.metrics.MetricsService;
import org.ametiste.metrics.aop.IdentifierResolver;
import org.ametiste.metrics.container.MapContainer;
import org.ametiste.metrics.resolver.MetricsIdentifierResolver;
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
@Import({
        JmxConfiguration.class,
        StatsDConfiguration.class,
        MetricsRoutingCoreConfiguration.class,
        MetricsIdentifierResolverCoreConfguration.class})
@EnableConfigurationProperties(MetricsProperties.class)
public class MetricsServiceConfiguration {

    @Autowired
    private MetricsProperties properties;

    @Autowired
    private MapContainer metricRoutingMap;

    @Autowired
    private MetricsIdentifierResolver identifierResolver;

    @Bean
    public AggregatorsRouter aggregatorsRouter() {
        return new MappingAggregatorsRouter(metricRoutingMap);
    }

    @Bean
    @Qualifier("metricsService")
    public MetricsService metricsService() {
       return new AggregatingMetricsService(
               aggregatorsRouter(),
               identifierResolver,
               properties.getPrefix()
       );
    }

    @Bean
    public SpelExpressionParser spelParser() {
        return new SpelExpressionParser();
    }

    @Bean
    public IdentifierResolver nameResolver() {
        return new IdentifierResolver(spelParser());
    }

}
