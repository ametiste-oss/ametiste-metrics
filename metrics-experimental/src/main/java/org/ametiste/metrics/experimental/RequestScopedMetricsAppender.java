package org.ametiste.metrics.experimental;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;import java.lang.Exception;import java.lang.Object;import java.lang.Override;

@Component
public class RequestScopedMetricsAppender extends HandlerInterceptorAdapter {

    // TODO: I want to do ResponseHeadersAppenderSource and ResponseHeadersAppend in the ifaces
    // TODO: I want to feed headers throug it, it would be general solution
    @Autowired(required = false)
    private RequestScopedMetricsAggregator requestScopedMetricsAggregator;

    private final Logger logger = LoggerFactory.getLogger(getClass());

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
