package org.ametiste.metrics.experimental;

import org.ametiste.metrics.MetricsAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;import java.lang.Override;

/**
 *
 * @since
 */
@Configuration
public class RequestScopedMetricsConfiguration extends WebMvcConfigurerAdapter {

    @Autowired(required = false)
    private RequestScopedMetricsAppender metricsAppender;

    @Bean
    public MetricsAggregator requestScopedMetricsAggregator() {
        return new AggregationActivator(new DumbAggreagator(), () -> { return false; });
    }

//    @Bean
//    @Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
//    public RequestScopedMetricsAggregator requestScopedAggregator() {
//        return new RequestScopedMetricsAggregator();
//    }
//
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        if (metricsAppender != null) {
            registry.addInterceptor(metricsAppender);
        }
    }

}
