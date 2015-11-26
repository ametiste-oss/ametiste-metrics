package org.ametiste.metrics.filter.extractor

import spock.lang.Specification

import javax.servlet.http.HttpServletRequest

/**
 * Created by ametiste on 11/26/15.
 */
class CommonPathExtractorTest extends Specification {

    HttpServletRequest request = Mock()

    def getPath() {
        given: "HttpServletRequest to extract path information"
            CommonPathExtractor extractor = new CommonPathExtractor()
        when: "Common extractor get path is called"
            String path = extractor.getPath(request)
        then: "PathInfo from request is returned"
            1*request.getPathInfo() >> "path"
            path.equals("path")
    }
}
