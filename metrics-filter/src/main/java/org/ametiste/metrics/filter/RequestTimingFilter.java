package org.ametiste.metrics.filter;

import org.ametiste.metrics.MetricsService;
import org.ametiste.metrics.resolver.MetricsIdentifierResolver;

import javax.servlet.*;
import java.io.IOException;

/**
 * Filter for intercepting request and registering request total processing time metric
 * Can be used with spring delegating filter
 *
 * @author ametiste
 * @since 0.1.0
 */
public class RequestTimingFilter implements Filter {

    private final MetricsIdentifierResolver resolver;
    private final MetricsService service;

    public RequestTimingFilter(MetricsService service, MetricsIdentifierResolver resolver) {
        if(service ==null || resolver ==null) {
            throw new IllegalArgumentException("MetricService and MetricIdentifierResolver cant be null, " +
                    "however at least one is null");
        }
        this.service = service;
        this.resolver = resolver;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {

        long startTime = System.currentTimeMillis();
        chain.doFilter(request, response);
        long endTime = System.currentTimeMillis();

        String metricName = RequestToMetricIdConverter.convert(request, resolver);
        service.createEvent(metricName, (int) (endTime - startTime));
    }

    @Override
    public void destroy() {

    }

}
