package org.ametiste.metrics.experimental;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class RequestMetricAppender extends HandlerInterceptorAdapter {

    private final MetricProvider metricProvider;

    private final static class RequestMetricConsumer implements MetricConsumer {

        private final HttpServletResponse response;

        public RequestMetricConsumer(HttpServletResponse response) {
            this.response = response;
        }

        @Override
        public void consume(String name, String value) {
            response.addHeader("X-Dph-Metric-" + name, value);
        }
    }

    public RequestMetricAppender(MetricProvider metricProvider) {
        this.metricProvider = metricProvider;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) throws Exception {
        metricProvider.provideMetricValues(new RequestMetricConsumer(response));
    }

}
