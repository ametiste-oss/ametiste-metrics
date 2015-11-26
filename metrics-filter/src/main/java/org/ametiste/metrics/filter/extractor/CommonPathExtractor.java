package org.ametiste.metrics.filter.extractor;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by ametiste on 11/26/15.
 */
public class CommonPathExtractor implements PathExtractor {

    @Override
    public String getPath(HttpServletRequest request) {
        String path = request.getPathInfo();
        return path;
    }
}
