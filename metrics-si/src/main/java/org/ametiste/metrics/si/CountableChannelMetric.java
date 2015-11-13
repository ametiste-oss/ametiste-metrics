package org.ametiste.metrics.si;

import org.ametiste.metrics.MetricsService;
import org.springframework.messaging.Message;

public class CountableChannelMetric extends AbstractChannelMetric {

    public CountableChannelMetric(MetricsService service, String metricName) {
        super(service, metricName);
    }

    @Override
	//TODO think we can do that with annotations
	protected Object doInvoke(ExecutionCallback callback, Object target, Message<?> message) throws Exception {
		Object result = callback.execute();
		service.increment(metricName,1);
		return result;
	}

}
