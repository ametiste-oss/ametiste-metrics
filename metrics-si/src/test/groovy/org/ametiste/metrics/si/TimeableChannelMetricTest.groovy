package org.ametiste.metrics.si

import org.ametiste.metrics.MetricsService
import org.springframework.integration.handler.advice.AbstractRequestHandlerAdvice
import org.springframework.messaging.Message
import spock.lang.Specification

/**
 * Created by ametiste on 11/13/15.
 */
class TimeableChannelMetricTest extends Specification {

    private MetricsService service = Mock()
    private TimeableChannelMetric handler = new TimeableChannelMetric(service, "metricName");

    def initialization() {
        when: "handler is initialized with null service"
            new TimeableChannelMetric(null, "metricName")
        then: "exception is thrown"
            thrown(IllegalArgumentException)
        when: "handler is initialized with null metric name"
            new TimeableChannelMetric(service, null)
        then: "exception is thrown"
            thrown(IllegalArgumentException)
        when: "handler is initialized with empty metric name"
            new TimeableChannelMetric(service, "")
        then: "exception is thrown"
            thrown(IllegalArgumentException)
    }


    def normalInvoke() {
        given: ""
            AbstractRequestHandlerAdvice.ExecutionCallback callback = Mock()
            Object object = Mock()
            Message message = Mock()
        when: "method is executed normally"
            callback.execute() >> "methodResult"
            Object result = handler.doInvoke(callback, object, message)
        then: "result isnt changed by handler, and  metric service is called once"
            result == "methodResult"
            1* service.createEvent("metricName", _)
    }

    def errorInvoke() {
        given: ""
            AbstractRequestHandlerAdvice.ExecutionCallback callback = Mock()
            Object object = Mock()
            Message message = Mock()
        when: "method is executed with error"
            callback.execute() >> {throw new Exception()}
            handler.doInvoke(callback, object, message)
        then: "error is rethrown, and  metric service is not called"
            thrown(Exception)
            0* service.increment("metricName", _)
    }
}
