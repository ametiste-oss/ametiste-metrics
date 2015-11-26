package org.ametiste.metrics.filter.extractor;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by ametiste on 11/26/15.
 */
public interface PathExtractor {
    String getPath(HttpServletRequest request);
}
