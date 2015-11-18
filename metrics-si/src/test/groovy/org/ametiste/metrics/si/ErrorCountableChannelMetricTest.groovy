package org.ametiste.metrics.si

import org.ametiste.metrics.MetricsService
import org.springframework.integration.handler.advice.AbstractRequestHandlerAdvice
import org.springframework.messaging.Message
import spock.lang.Specification

/**
 * Created by ametiste on 11/13/15.
 */
class ErrorCountableChannelMetricTest extends Specification {

    private MetricsService service = Mock()
    private ErrorCountableChannelMetric handler = new ErrorCountableChannelMetric(service, "metricName");

    def initialization() {
        when: "handler is initialized with null service"
            new ErrorCountableChannelMetric(null, "metricName")
        then: "exception is thrown"
            thrown(IllegalArgumentException)
        when: "handler is initialized with null metric name"
            new ErrorCountableChannelMetric(service, null)
        then: "exception is thrown"
            thrown(IllegalArgumentException)
        when: "handler is initialized with empty metric name"
            new ErrorCountableChannelMetric(service, "")
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
        then: "result isnt changed by handler, and  metric service is not called"
            result == "methodResult"
            0* service.increment("metricName", 1)
    }

    def errorInvoke() {
        given: ""
            AbstractRequestHandlerAdvice.ExecutionCallback callback = Mock()
            Object object = Mock()
            Message message = Mock()
        when: "method is executed with error"
            callback.execute() >> {throw new Exception()}
            handler.doInvoke(callback, object, message)
        then: "error is rethrown, and  metric service is called once"
            thrown(Exception)
            1* service.increment("metricName", 1)
    }
}
