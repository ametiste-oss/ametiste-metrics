package org.ametiste.metrics.filter.extractor

import spock.lang.Specification

import javax.servlet.http.HttpServletRequest

/**
 * Created by ametiste on 11/26/15.
 */
class SpringPathExtractorTest extends Specification {

    HttpServletRequest request = Mock()

    def getPath() {
        given: "HttpServletRequest to extract path information"
            SpringPathExtractor extractor = new SpringPathExtractor()
        when: "Common extractor get path is called"
            String path = extractor.getPath(request)
        then: "PathInfo from request is returned"
            1*request.getServletPath() >> "path"
            path.equals("path")
    }
}
