package org.ametiste.metrics.aop

import org.ametiste.metrics.MetricsService
import org.ametiste.metrics.annotations.Chronable
import org.ametiste.metrics.annotations.composite.Chronables
import org.ametiste.metrics.aop.stubs.StubChronable
import org.ametiste.metrics.aop.stubs.StubChronables
import org.aspectj.lang.JoinPoint
import org.springframework.expression.Expression
import org.springframework.expression.ExpressionParser
import spock.lang.Specification


/**
 * Created by atlantis on 11/7/15.
 */
class ChronableAspectTest extends Specification {

    private MetricsService service = Mock()
    private IdentifierResolver resolver = Mock()
    private ExpressionParser parser = Mock()
    private ChronableAspect aspect = new ChronableAspect(service, resolver, parser)


    def initialization() {
        when: "aspect is initialized with null metric service"
            new ChronableAspect(null, resolver, parser)
        then: "exception is thrown"
            thrown(IllegalArgumentException)
        when: "aspect is initialized with null resolver"
            new ChronableAspect(service, null, parser)
        then: "exception is thrown"
            thrown(IllegalArgumentException)
        when: "aspect is initialized with null parser"
            new ChronableAspect(service, resolver, null)
        then: "exception is thrown"
            thrown(IllegalArgumentException)
    }


    def processTimingNoException() {
        given: "metric with normal result and annotation"
            JoinPoint jp = Mock()
            Object result = Mock()
            Chronable annotation = new StubChronable("metricName", "", "5", "", "'true'", Chronable.NO_EXCEPTION)
        when: "metric is processed"
            Expression expression = Mock()
            parser.parseExpression("'true'") >> expression
            resolver.getTargetIdentifier(_,_,_) >> "metricName"
            expression.getValue(_, boolean.class) >> true
            aspect.processTiming(jp, result, annotation)
        then: "service method is called"
            1 * service.createEvent("metricName", 5);
    }

    def processTimingParserException() {
        given: "metric with normal result and annotation"
            JoinPoint jp = Mock()
            Object result = Mock()
            Chronable annotation = new StubChronable("metricName", "", "5", "", "'true'", Chronable.NO_EXCEPTION)
        when: "metric is processed and parser throws exception"
            Expression expression = Mock()
            parser.parseExpression("'true'") >> {throw new Exception()}
            resolver.getTargetIdentifier(_,_,_) >> "metricName"
            expression.getValue(_, boolean.class) >> true
            aspect.processTiming(jp, result, annotation)
        then: "service method is not called"
            0 * service.createEvent(_, _);
    }

    def processTimingNoExceptionWithValueExp() {
        given: "metric with normal result and annotation with value expression"
            JoinPoint jp = Mock()
            Object result = Mock()
            Chronable annotation = new StubChronable("metricName", "", "", "12", "'true'", Chronable.NO_EXCEPTION)
        when: "metric is processed"
            Expression expression = Mock()
            parser.parseExpression("12") >> expression
            expression.getValue(_, Integer.class) >> 12
            parser.parseExpression("'true'") >> expression
            resolver.getTargetIdentifier(_,_,_) >> "metricName"
            expression.getValue(_, boolean.class) >> true
            aspect.processTiming(jp, result, annotation)
        then: "service method is called"
            1 * service.createEvent("metricName", 12);
    }

    def processTimingWithEmtyNameAndNameExp() {
        given: "metric with normal result and annotation with empty value and expression"
            JoinPoint jp = Mock()
            Object result = Mock()
            Chronable annotation = new StubChronable("", "", "", "", "'true'", Chronable.NO_EXCEPTION)
        when: "metric is processed"
            Expression expression = Mock()
            parser.parseExpression("'true'") >> expression
            resolver.getTargetIdentifier(_,_,_) >> "metricName"
            expression.getValue(_, boolean.class) >> true
            aspect.processTiming(jp, result, annotation)
        then: "exception is thrown and not catched"
            thrown(IllegalArgumentException.class)
    }

    def processTimingNoExceptionWithFalseCondition() {
        given: "metric with normal result and annotation"
            JoinPoint jp = Mock()
            Object result = Mock()
            Chronable annotation = new StubChronable("metricName", "", "5", "", "'true'", Chronable.NO_EXCEPTION)
        when: "metric is processed and conition is false"
            Expression expression = Mock()
            parser.parseExpression("'true'") >> expression
            resolver.getTargetIdentifier(_,_,_) >> "metricName"
            expression.getValue(_, boolean.class) >> false
            aspect.processTiming(jp, result, annotation)
        then: "service method is not called"
            0 * service.createEvent("metricName", 5);
    }

    def processTimingWithException() {
        given: "metric with illegal argument exception and annotation"
            JoinPoint jp = Mock()
            Exception exception = new IllegalArgumentException()
            Chronable annotation = new StubChronable("metricName", "", "5", "", "'true'", IllegalArgumentException.class)
        when: "metric is processed"
            Expression expression = Mock()
            parser.parseExpression("'true'") >> expression
            resolver.getTargetIdentifier(_,_,_) >> "metricName"
            expression.getValue(_, boolean.class) >> true
            aspect.processTiming(jp, exception, annotation)
        then: "service method is called"
           1 * service.createEvent("metricName", 5);
    }

    def processTimingBatchWithException() {
        given: "metric with illegal argument exception and annotation"
            JoinPoint jp = Mock()
            Exception exception = new IllegalArgumentException()
            Chronable annotation = new StubChronable("metricName", "", "5", "", "'true'", IllegalArgumentException.class)
            Chronables batch = new StubChronables(annotation, annotation)
        when: "metric is processed"
            Expression expression = Mock()
            parser.parseExpression("'true'") >> expression
            resolver.getTargetIdentifier(_,_,_) >> "metricName"
            expression.getValue(_, boolean.class) >> true
            aspect.processTimingBatch(jp, exception, batch)
        then: "service method is called"
            2 * service.createEvent("metricName", 5);
    }

    def processTimingBatchNoException() {
        given: "metric with normal result and annotation"
            JoinPoint jp = Mock()
            Object result = Mock()
            Chronable annotation = new StubChronable("metricName", "", "5", "", "'true'", Chronable.NO_EXCEPTION)
            Chronables batch = new StubChronables(annotation, annotation)
        when: "metric is processed"
            Expression expression = Mock()
            parser.parseExpression("'true'") >> expression
            resolver.getTargetIdentifier(_,_,_) >> "metricName"
            expression.getValue(_, boolean.class) >> true
            aspect.processTimingBatch(jp, result, batch)
        then: "service method is called"
            2 * service.createEvent("metricName", 5);
    }

}

