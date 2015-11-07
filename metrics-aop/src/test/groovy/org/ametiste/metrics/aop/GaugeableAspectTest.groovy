package org.ametiste.metrics.aop

import org.ametiste.metrics.MetricsService
import org.ametiste.metrics.annotations.Gaugeable
import org.ametiste.metrics.annotations.composite.Gaugeables
import org.ametiste.metrics.aop.stubs.StubGaugables
import org.ametiste.metrics.aop.stubs.StubGaugeable
import org.aspectj.lang.JoinPoint
import spock.lang.Specification

/**
 * Created by atlantis on 11/7/15.
 */
class GaugeableAspectTest extends Specification {


    private MetricsService service = Mock()
    private IdentifierResolver resolver = Mock()
    private GaugeableAspect aspect = new GaugeableAspect(service, resolver)

    def initialization() {
        when: "aspect is initialized with null metric service"
            new GaugeableAspect(null, resolver)
        then: "exception is thrown"
            thrown(IllegalArgumentException)
        when: "aspect is initialized with null resolver"
            new GaugeableAspect(service, null)
        then: "exception is thrown"
            thrown(IllegalArgumentException)
    }

    def processCounting() {
        given: "join point and gaugeable annotation"
            JoinPoint jp = Mock()
            Gaugeable gaugeable = new StubGaugeable("","", 6)
            Object result = Mock()
        when: "resolver resolves identifier normally"
            resolver.getTargetIdentifier("", "",_) >> "metricName"
            aspect.processCounting(jp,result, gaugeable )
        then: "service gauge is called with 6"
            1*service.gauge("metricName", 6)
    }

    def processCountingParserException() {
        given: "join point and gaugeable  annotation"
            JoinPoint jp = Mock()
            Gaugeable gaugeable = new StubGaugeable("","", 6)
            Object result = Mock()
        when: "resolver resolves identifier normally"
            resolver.getTargetIdentifier("", "",_) >> {throw new MetricExpressionParsingException("")}
            aspect.processCounting(jp,result,gaugeable )
        then: "service increment is not called"
            0*service.gauge("metricName", 6)
    }

    def processCountingBatch() {
        given: "join point and gaugeable annotation"
            JoinPoint jp = Mock()
            Object result = Mock()
            Gaugeable gaugeable = new StubGaugeable("","", 6)
            Gaugeables batch = new StubGaugables(gaugeable, gaugeable)
        when: "resolver resolves identifier normally"
            resolver.getTargetIdentifier("", "",_) >> "metricName"
            aspect.processCounting(jp,result,batch)
        then: "service gauge is called with 6 twice"
            2*service.gauge("metricName", 6)
    }
}
