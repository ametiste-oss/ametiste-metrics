package org.ametiste.metrics.boot.configuration;

import org.ametiste.metrics.AggregatingMetricsService;
import org.ametiste.metrics.MetricsService;
import org.ametiste.metrics.aop.IdentifierResolver;
import org.ametiste.metrics.resolver.MetricsIdentifierResolver;
import org.ametiste.metrics.router.AggregatorsRouter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.expression.spel.standard.SpelExpressionParser;

@Configuration
@Import({
        DefaultAggreagtorsConfiguration.class,
        DefaultRoutingConfiguration.class,
        MetricsRoutingCoreConfiguration.class,
        MetricsIdentifierResolverCoreConfguration.class})
@EnableConfigurationProperties(MetricsProperties.class)
public class MetricsServiceConfiguration {

    @Autowired
    private MetricsProperties properties;

    @Autowired
    private AggregatorsRouter aggregatorsRouter;

    @Autowired
    @CoreIdentifierResolver
    private MetricsIdentifierResolver identifierResolver;

    @Bean
    @Qualifier("metricsService")
    @ConditionalOnMissingBean
    public MetricsService metricsService() {
        return new AggregatingMetricsService(
                aggregatorsRouter,
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
