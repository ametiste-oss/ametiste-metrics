package org.ametiste.metrics.filter

import org.ametiste.metrics.filter.extractor.PathExtractor
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
    private PathExtractor extractor = Mock()
    private RequestToMetricIdConverter converter = new RequestToMetricIdConverter(extractor)

    def init() {
        when: "Converter is initialized with null extractor"
            new RequestToMetricIdConverter(null)
        then: "Exception is thrown"
            thrown(IllegalArgumentException)
    }

    def convertNormal() {
        given: "Good http request"
            HttpServletRequest request = Mock()
        when: ""
            extractor.getPath(request) >> "resolveMe"
            resolver.resolveMetricId("resolveMe") >> "metricName"
            String name = converter.convert(request, resolver)
        then: ""
            "metricName" == name
    }
    def convertNonHttpServlet() {
        given: "Good http request"
            ServletRequest request = Mock()
        when: ""
            resolver.resolveMetricId("resolveMe") >> "metricName"
            String name = converter.convert(request, resolver)
        then: ""
            thrown(ServletException)
    }
}
