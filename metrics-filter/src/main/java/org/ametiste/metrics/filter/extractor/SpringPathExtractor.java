package org.ametiste.metrics.filter.extractor;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by ametiste on 11/26/15.
 */
public class SpringPathExtractor implements PathExtractor {
    @Override
    public String getPath(HttpServletRequest request) {
        return request.getServletPath();
    }
}
