package org.ametiste.metrics.aop

import org.ametiste.metrics.MetricsService
import org.ametiste.metrics.annotations.MetricsMode
import org.ametiste.metrics.annotations.Timeable
import org.ametiste.metrics.annotations.composite.Timeables
import org.ametiste.metrics.aop.stubs.StubTimeable
import org.ametiste.metrics.aop.stubs.StubTimeables
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.ProceedingJoinPoint
import spock.lang.Specification

/**
 * Created by atlantis on 11/7/15.
 */
class TimeableAspectTest extends Specification {


    private MetricsService service = Mock()
    private IdentifierResolver resolver = Mock()
    private TimeableAspect aspect = new TimeableAspect(service, resolver)
    private ProceedingJoinPoint pjp

    def setup() {
        pjp = Mock()
        pjp.args >> new Object()
        pjp.target >> new Object()
    }

    def initialization() {
        when: "aspect is initialized with null metric service"
            new TimeableAspect(null, resolver)
        then: "exception is thrown"
            thrown(IllegalArgumentException)
        when: "aspect is initialized with null resolver"
            new TimeableAspect(service, null)
        then: "exception is thrown"
            thrown(IllegalArgumentException)
    }

    def processTiming() {
        given: "join point with timeable annotation"
            Timeable timeable = new StubTimeable("", "", MetricsMode.ERROR_FREE)
        when: "resolver resolves name normally"
            resolver.getTargetIdentifier("", "",_) >> "metricName"
            aspect.processTiming(pjp, timeable)
        then: "service create event is called witih some value(time, cant be predicted)"
           1* service.createEvent("metricName",_)
    }

    def processTimingErrorFree() {
        given: "join point with timeable with error free mode"
            Timeable timeable = new StubTimeable("", "", MetricsMode.ERROR_FREE)
            Exception e = new Exception();
        when: "resolver resolves name normally but join point proceeds with error"
            pjp.proceed() >> {throw e}
            resolver.getTargetIdentifier("", "",_) >> "metricName"
            aspect.processTiming(pjp, timeable)
        then: "exception is thrown, no service call"
            thrown(Exception)
            0* service.createEvent("metricName",_)

    }

    def processTimingErrorProne() {
        given: "join point with timeable with error prone mode"
            Timeable timeable = new StubTimeable("", "", MetricsMode.ERROR_PRONE)
            Exception e = new Exception();
        when: "resolver resolves name normally but join point proceeds with error"
            pjp.proceed() >> {throw e}
            resolver.getTargetIdentifier("", "",_) >> "metricName"
            aspect.processTiming(pjp, timeable)
        then: "exception is thrown but service is called ones with  some value"
            thrown(Exception)
            1* service.createEvent("metricName",_)

    }

    def processTimingWithParserError() {
        given: "join point with timeable"
            Timeable timeable = new StubTimeable("", "", MetricsMode.ERROR_FREE)
        when: "resolver throws parse exception"
            resolver.getTargetIdentifier("", "",_) >> {throw new MetricExpressionParsingException("")}
            aspect.processTiming(pjp, timeable)
        then: "no service call but no exception"
            0* service.createEvent("metricName",_)
    }

    def processTimingBatch() {
        given: "join point with timeable"
            Timeable timeable = new StubTimeable("", "", MetricsMode.ERROR_PRONE)
            Timeables timeables = new StubTimeables(timeable, timeable)
        when: "resolver resolves name normally"
            resolver.getTargetIdentifier("", "",_) >> "metricName"
            aspect.processTimingBatch(pjp, timeables)
        then: "service method is called twice, for each annotation"
            2* service.createEvent("metricName",_)
    }



}
