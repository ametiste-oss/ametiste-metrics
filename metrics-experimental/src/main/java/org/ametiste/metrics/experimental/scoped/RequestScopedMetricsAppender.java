package org.ametiste.metrics.experimental.scoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// TODO: dicumentation
// TODO: known bug, does not work with redgreen failover line controller, have no idea wahy
public class RequestScopedMetricsAppender extends HandlerInterceptorAdapter {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    // TODO: I want to do ResponseHeadersAppenderSource and ResponseHeadersAppend in the ifaces
    // TODO: I want to feed headers throug it, it would be general solution
    @Autowired(required = false)
    private RequestScopedMetricsAggregator requestScopedMetricsAggregator;

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        if (requestScopedMetricsAggregator == null) {
            // NOTE: to prevent errors with rare specnfigurationial where
            // aggregator is not presented, but appender is.
            if (logger.isDebugEnabled()) {
                logger.warn("Inconsistent configuration: scoped metrics appender without scoped aggregator.");
            }
            return;
        }

        requestScopedMetricsAggregator.consumeMetrics(
                // TODO: need parameter to define header name
                (k, v) -> response.addHeader("Ame-Request-Metric", k + "=" + v)
        );

    }
}
