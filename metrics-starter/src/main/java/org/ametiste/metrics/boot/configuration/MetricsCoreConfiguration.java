package org.ametiste.metrics.boot.configuration;

import org.ametiste.metrics.MetricsService;
import org.ametiste.metrics.aop.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.expression.spel.standard.SpelExpressionParser;


@Configuration
@Import(MetricsServiceConfiguration.class)
public class MetricsCoreConfiguration {

    @Autowired
    private MetricsService metricsService;

    @Autowired
    private SpelExpressionParser spelParser;

    @Autowired
    private IdentifierResolver nameResolver;

    @Bean
    public TimeableAspect timeableAspect() {
        return new TimeableAspect(metricsService, nameResolver);
    }

    @Bean
    public CountableAspect countableAspect() {
        return new CountableAspect(metricsService, nameResolver);
    }

    @Bean
    public ErrorCountableAspect errorCountableAspect() {
        return new ErrorCountableAspect(metricsService, nameResolver);
    }

    @Bean
    public ChronableAspect chronableAspect() {
        return new ChronableAspect(metricsService, nameResolver, spelParser);
    }

}
