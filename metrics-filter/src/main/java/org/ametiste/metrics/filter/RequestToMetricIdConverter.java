package org.ametiste.metrics.filter;

import org.ametiste.metrics.resolver.MetricsIdentifierResolver;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

/**
 * Helper class for resolving metric name from request. Uses path info and metric name resolver for that
 *
 * @author ametiste
 * @since 0.1.0
 */
public class RequestToMetricIdConverter {

    public static String convert(ServletRequest request, MetricsIdentifierResolver resolver) throws ServletException {

        if (!(request instanceof HttpServletRequest)) {
            throw new ServletException("Filter only supports HTTP requests");
        }
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        return resolver.resolveMetricId(httpRequest.getPathInfo());

    }
}
