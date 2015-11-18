package org.ametiste.metrics.si;

import org.ametiste.metrics.MetricsService;
import org.springframework.integration.handler.advice.AbstractRequestHandlerAdvice;

/**
 * Created by ametiste on 11/13/15.
 */
public abstract class AbstractChannelMetric extends AbstractRequestHandlerAdvice {

    protected final MetricsService service;
    protected final String metricName;

    public AbstractChannelMetric(MetricsService service, String metricName) {
        if(service ==null) {
            throw new IllegalArgumentException("Service cant be null");
        }
        if(metricName == null || metricName.isEmpty()) {
            throw new IllegalArgumentException("Metric name cant be null or empty");
        }
        this.service = service;
        this.metricName = metricName;
    }

}
