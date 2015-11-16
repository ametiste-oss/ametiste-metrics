package org.ametiste.metrics.si;

import org.ametiste.metrics.MetricsService;
import org.springframework.messaging.Message;

public class ErrorCountableChannelMetric extends AbstractChannelMetric {

    public ErrorCountableChannelMetric(MetricsService service, String metricName) {
        super(service, metricName);
    }

    @Override
    protected Object doInvoke(ExecutionCallback callback, Object target, Message<?> message) throws Exception {
        try {
            return callback.execute();
        } catch (Exception e) {
            service.increment(metricName, 1);
            throw e;
        }

    }

}
