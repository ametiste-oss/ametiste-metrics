package org.ametiste.metrics.filter;

import org.ametiste.metrics.MetricsService;
import org.ametiste.metrics.resolver.MetricsIdentifierResolver;

import javax.servlet.*;
import java.io.IOException;

/**
 * Filter for intercepting request and registering request count number metric
 * Can be used with spring delegating filter
 *
 * @author ametiste
 * @since 0.1.0
 */
public class RequestCountFilter implements Filter {

    private final MetricsIdentifierResolver resolver;
    private final MetricsService service;

    public RequestCountFilter(MetricsService service, MetricsIdentifierResolver resolver) {
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

        String metricName = RequestToMetricIdConverter.convert(request, resolver);
        service.increment(metricName, 1);

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }

}
