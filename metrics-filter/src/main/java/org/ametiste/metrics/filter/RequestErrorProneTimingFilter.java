package org.ametiste.metrics.filter;

import org.ametiste.metrics.MetricsService;
import org.ametiste.metrics.resolver.MetricsIdentifierResolver;

import javax.servlet.*;
import java.io.IOException;

/**
 * Filter for intercepting request and registering request total processing error prone time metric
 * Can be used with spring delegating filter
 *
 * @author abliznyuk
 * @since 0.2.1
 */
public class RequestErrorProneTimingFilter implements Filter {

    private final MetricsIdentifierResolver resolver;
    private final MetricsService service;
    private final RequestToMetricIdConverter converter;

    public RequestErrorProneTimingFilter(MetricsService service, MetricsIdentifierResolver resolver, RequestToMetricIdConverter converter) {
        if (service == null || resolver == null || converter == null) {
            throw new IllegalArgumentException("MetricService, MetricIdentifierResolver and RequestToMetricIdConverter cant be null, " +
                    "however at least one is null");
        }
        this.service = service;
        this.resolver = resolver;
        this.converter = converter;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        long startTime = System.currentTimeMillis();
        try {
            chain.doFilter(request, response);
        } finally {
            long endTime = System.currentTimeMillis();
            registerRequestTime(request, startTime, endTime);
        }
    }

    @Override
    public void destroy() {
    }

    private void registerRequestTime(ServletRequest request, long startTime, long endTime) throws ServletException {
        String metricName = converter.convert(request, resolver);
        service.createEvent(metricName, (int) (endTime - startTime));
    }
}