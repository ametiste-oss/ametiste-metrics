package org.ametiste.metrics.experimental.scoped;

import org.ametiste.metrics.MetricsAggregator;
import org.ametiste.metrics.boot.configuration.CoreAggregator;
import org.ametiste.metrics.experimental.activator.AggregationActivator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 *
 * @since
 */
@Configuration
public class RequestScopedMetricsConfiguration extends WebMvcConfigurerAdapter {

    @Bean
    @CoreAggregator
    public MetricsAggregator requestScopedMetricsAggregator() {
        return new AggregationActivator(requestScopedAggregator(),
                () -> { return RequestScopedMetricsAggregator.isRequestScoped(); },
                () -> { return RequestScopedMetricsAggregator.isEnabledForRequest(); }
        );
    }

    /**
     * <p>
     *      Request-scoped bean to aggregate metrics within the concrete
     *      web request.
     * </p>
     *
     * <p>
     *      Note, this aggregator is not included into default configuration
     *      and designd for internal usage within the request-scoped metrics module.
     * </p>
     */
    @Bean
    @Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public RequestScopedMetricsAggregator requestScopedAggregator() {
        return new RequestScopedMetricsAggregator();
    }

    @Bean
    public RequestScopedMetricsAppender requestScopedMetricsAppender() {
         return new RequestScopedMetricsAppender();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestScopedMetricsAppender());
    }

}
