package org.ametiste.metrics.filter

import org.ametiste.metrics.resolver.MetricsIdentifierResolver
import spock.lang.Specification

import javax.servlet.ServletException
import javax.servlet.ServletRequest
import javax.servlet.http.HttpServletRequest

/**
 * Created by atlantis on 11/7/15.
 */
class RequestToMetricIdConverterTest extends Specification {

    private MetricsIdentifierResolver resolver = Mock()

    def convertNormal() {
        given: "Good http request"
            HttpServletRequest request = Mock()
        when: ""
            request.getPathInfo() >> "resolveMe"
            resolver.resolveMetricId("resolveMe") >> "metricName"
            String name = RequestToMetricIdConverter.convert(request, resolver)
        then: ""
            "metricName" == name
    }
    def convertNonHttpServlet() {
        given: "Good http request"
            ServletRequest request = Mock()
        when: ""
            resolver.resolveMetricId("resolveMe") >> "metricName"
            String name = RequestToMetricIdConverter.convert(request, resolver)
        then: ""
            thrown(ServletException)
    }
}
