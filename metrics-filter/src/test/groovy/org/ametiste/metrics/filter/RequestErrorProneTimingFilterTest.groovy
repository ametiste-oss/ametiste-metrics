package org.ametiste.metrics.filter

import org.ametiste.metrics.MetricsService
import org.ametiste.metrics.resolver.MetricsIdentifierResolver
import spock.lang.Specification

import javax.servlet.FilterChain
import javax.servlet.FilterConfig
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Created by atlantis on 11/7/15.
 */
class RequestErrorProneTimingFilterTest extends Specification {

    private MetricsService service = Mock(MetricsService)
    private MetricsIdentifierResolver resolver = Mock(MetricsIdentifierResolver)
    private RequestToMetricIdConverter converter = Mock(RequestToMetricIdConverter)
    private RequestErrorProneTimingFilter filter = new RequestErrorProneTimingFilter(service, resolver, converter)

    def initialization() {
        when: "filter is created with null service"
            new RequestErrorProneTimingFilter(null, resolver, converter)
        then: "exception should be thrown"
            thrown(IllegalArgumentException)
        when: "filter is created with null resolver"
            new RequestErrorProneTimingFilter(service, null, converter)
        then: "exception should be thrown"
            thrown(IllegalArgumentException)
        when: "filter is created with null converter"
            new RequestErrorProneTimingFilter(service, resolver, null)
        then: "exception should be thrown"
            thrown(IllegalArgumentException)
    }

    def defaults() {
        given: "some config"
            FilterConfig config = Mock(FilterConfig)
        when: "default filter methods are called"
            filter.init(config)
            filter.destroy()
        then: "Nothing happen and nothing broken"
    }

    def doFilter() {
        given: "request and response that are to be filtered"
            HttpServletRequest request = Mock(HttpServletRequest)
            HttpServletResponse response = Mock(HttpServletResponse)
            FilterChain chain = Mock(FilterChain)
        when: "request is called"
            converter.convert(request, resolver) >> "metricName"
            resolver.resolveMetricId(_) >> "metricName"
            filter.doFilter(request, response, chain)
        then: "service is called with increment, and chain proceeds"
            1 * service.createEvent("metricName", _)
            1 * chain.doFilter(request, response)
    }

    def chainProcessingFailure() {
        given: "request and response that are to be filtered"
            HttpServletRequest request = Mock(HttpServletRequest)
            HttpServletResponse response = Mock(HttpServletResponse)
            FilterChain chain = Mock(FilterChain)
        when: "request is called"
            converter.convert(request, resolver) >> "metricName"
            resolver.resolveMetricId(_) >> "metricName"
            filter.doFilter(request, response, chain)
        then: "chain processing is failed, but service is called with time event"
            1 * chain.doFilter(request, response) >> { throw new RuntimeException() }
            thrown(RuntimeException)
            1 * service.createEvent("metricName", _)
    }
}
