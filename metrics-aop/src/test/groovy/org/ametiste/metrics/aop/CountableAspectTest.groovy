package org.ametiste.metrics.aop

import org.ametiste.metrics.MetricsService
import org.ametiste.metrics.annotations.Countable
import org.ametiste.metrics.annotations.composite.Countables
import org.ametiste.metrics.aop.stubs.StubCountable
import org.ametiste.metrics.aop.stubs.StubCountables
import org.aspectj.lang.JoinPoint
import spock.lang.Specification

/**
 * Created by atlantis on 11/7/15.
 */
class CountableAspectTest extends Specification {

    private MetricsService service = Mock()
    private IdentifierResolver resolver = Mock()
    private CountableAspect aspect = new CountableAspect(service, resolver)
    private JoinPoint jp

    def setup() {
        jp = Mock()
        jp.args >> new Object()
        jp.target >> new Object()
    }

    def initialization() {
        when: "aspect is initialized with null metric service"
            new CountableAspect(null, resolver)
        then: "exception is thrown"
            thrown(IllegalArgumentException)
        when: "aspect is initialized with null resolver"
            new CountableAspect(service, null)
        then: "exception is thrown"
            thrown(IllegalArgumentException)
    }

    def processCounting() {
        given: "join point with normal result and countable annotation"
            Object result = Mock()
            Countable countable = new StubCountable("","", 5)
        when: "resolver resolves identifier normally"
            resolver.getTargetIdentifier("", "",_) >> "metricName"
            aspect.processCounting(jp, result,countable )
        then: "service increment is called"
            1*service.increment("metricName", 5)
    }

    def processCountingParserException() {
        given: "join point with normal result and countable annotation"
            Object result = Mock()
            Countable countable = new StubCountable("","", 5)
        when: "resolver throws excetion"
            resolver.getTargetIdentifier("", "",_) >>{ throw new MetricExpressionParsingException("")}
            aspect.processCounting(jp, result,countable )
        then: "service increment isnt called"
            0*service.increment("metricName", 5)
    }

    def processCountingBatch() {
        given: "join point with normal result and countable annotation"
            Object result = Mock()
            Countable countable = new StubCountable("","", 5)
            Countables batch = new StubCountables(countable, countable)
        when: "resolver throws excetion"
            resolver.getTargetIdentifier("", "",_) >> "metricName"
            aspect.processCounting(jp, result,batch )
        then: "service increment isnt called"
            2*service.increment("metricName", 5)
    }
}
