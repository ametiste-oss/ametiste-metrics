package org.ametiste.metrics.filter;

import org.ametiste.metrics.MetricsService;
import org.ametiste.metrics.resolver.MetricsNameResolver;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Filter for intercepting request and registering request count number metric
 * Can be used with spring delegating filter
 * @author ametiste
 * @since 0.1.0
 */
public class RequestCountFilter implements Filter {

	private final MetricsNameResolver resolver;
	private final MetricsService service;

	public RequestCountFilter(MetricsService service, MetricsNameResolver resolver) {
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
		service.increment(metricName);

		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {

	}

}
