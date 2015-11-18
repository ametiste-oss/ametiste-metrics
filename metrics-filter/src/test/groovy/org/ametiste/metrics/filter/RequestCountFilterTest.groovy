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
class RequestCountFilterTest extends Specification {

    private MetricsService service = Mock()
    private MetricsIdentifierResolver resolver = Mock()
    private RequestCountFilter filter = new RequestCountFilter(service, resolver)


    def initialization() {
        when: "filter is created with null service"
            new RequestCountFilter(null, resolver)
        then: "exception should be thrown"
            thrown(IllegalArgumentException)
        when: "filter is created with null resolver"
            new RequestCountFilter(service, null)
        then: "exception should be thrown"
            thrown(IllegalArgumentException)
    }
    def defaults() {
        given: "some config"
            FilterConfig config = Mock()
        when: "default filter methods are called"
            filter.init(config)
            filter.destroy()
        then: "Nothing happen and nothing broken"
    }

    def doFilter() {
        given: "request and response that are to be filtered"
            HttpServletRequest request = Mock()
            HttpServletResponse response = Mock()
            FilterChain chain = Mock()
        when: "request is called"
            resolver.resolveMetricId(_) >> "metricName"
            filter.doFilter(request, response, chain)
        then: "service is called with increment, and chain proceeds"
            1* service.increment("metricName",1)
            1* chain.doFilter(request, response)
    }


}
