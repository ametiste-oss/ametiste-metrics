package org.ametiste.metrics.aop

import org.ametiste.metrics.MetricsService
import org.ametiste.metrics.annotations.ErrorCountable
import org.ametiste.metrics.annotations.composite.ErrorCountables
import org.ametiste.metrics.aop.stubs.StubErrorCountable
import org.ametiste.metrics.aop.stubs.StubErrorCountables
import org.aspectj.lang.JoinPoint
import spock.lang.Specification

/**
 * Created by atlantis on 11/7/15.
 */
class ErrorCountableAspectTest extends Specification {

    private MetricsService service = Mock()
    private IdentifierResolver resolver = Mock()
    private ErrorCountableAspect aspect = new ErrorCountableAspect(service, resolver)

    def initialization() {
        when: "aspect is initialized with null metric service"
            new ErrorCountableAspect(null, resolver)
        then: "exception is thrown"
            thrown(IllegalArgumentException)
        when: "aspect is initialized with null resolver"
            new ErrorCountableAspect(service, null)
        then: "exception is thrown"
            thrown(IllegalArgumentException)
    }

    def processCounting() {
        given: "join point and error countable annotation"
            JoinPoint jp = Mock()
            ErrorCountable countable = new StubErrorCountable("","")
        when: "resolver resolves identifier normally"
            resolver.getTargetIdentifier("", "",_) >> "metricName"
            aspect.processCounting(jp,countable )
        then: "service increment is called with 1"
            1*service.increment("metricName", 1)
    }

    def processCountingParserException() {
        given: "join point and error countable annotation"
            JoinPoint jp = Mock()
            ErrorCountable countable = new StubErrorCountable("","")
        when: "resolver resolves identifier normally"
            resolver.getTargetIdentifier("", "",_) >> {throw new MetricExpressionParsingException("")}
            aspect.processCounting(jp,countable )
        then: "service increment is not called"
            0*service.increment("metricName", 1)
    }

    def processCountingBatch() {
        given: "join point with and error countable annotation"
            JoinPoint jp = Mock()
            ErrorCountable countable = new StubErrorCountable("","")
            ErrorCountables batch = new StubErrorCountables(countable, countable)
        when: "resolver resolves identifier normally"
            resolver.getTargetIdentifier("", "",_) >> "metricName"
            aspect.processCountingBatch(jp,batch)
        then: "service increment is called with 1 twice"
            2*service.increment("metricName", 1)
    }


}
