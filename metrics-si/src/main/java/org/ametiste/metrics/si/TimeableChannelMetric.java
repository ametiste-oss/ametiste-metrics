package org.ametiste.metrics.si;

import org.ametiste.metrics.MetricsService;
import org.springframework.messaging.Message;

public class TimeableChannelMetric extends AbstractChannelMetric {


	public TimeableChannelMetric(MetricsService service, String metricName) {
		super(service, metricName);
	}

	@Override
    //TODO add error-prone metric
	protected Object doInvoke(ExecutionCallback callback, Object target, Message<?> message) throws Exception {

		long start = System.currentTimeMillis();
		Object result = callback.execute();
		long end = System.currentTimeMillis();

		service.createEvent(metricName, (int)(end-start));
		return result;
	}

}
