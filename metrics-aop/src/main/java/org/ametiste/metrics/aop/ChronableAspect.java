package org.ametiste.metrics.aop;

import org.ametiste.metrics.MetricsService;
import org.ametiste.metrics.annotations.Chronable;
import org.ametiste.metrics.annotations.composite.Chronables;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;


/**
 * Aspect for event registration in time for methods that annotated with
 * {@link Chronable} annotation. Increments metric with identifier, resolved from
 * {@link Chronable#name()} and {@link Chronable#nameSuffixExpression()} for value
 * {@link Chronable#value()}
 *
 * @author ametiste
 * @since 0.1.0
 */
@Aspect
public class ChronableAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private MetricsService service;

    private ExpressionParser parser;

    private IdentifierResolver resolver;

    public ChronableAspect(MetricsService service, IdentifierResolver resolver, ExpressionParser parser) {

        if (service == null || resolver == null || parser == null) {
            throw new IllegalArgumentException("MetricsService, IdentifierResolver and ExpressionParser cant be null, however one of it is null");
        }
        this.service = service;
        this.resolver = resolver;
        this.parser = parser;
    }

    @Pointcut(value = "@annotation(chron)", argNames = "chron")
    public void chronate(Chronable chron) {
    }

    @Pointcut(value = "@annotation(chrons)", argNames = "chrons")
    public void chronateBatch(Chronables chrons) {
    }

    @AfterReturning(pointcut = "chronateBatch(chrons)", returning = "returnedObject")
    public void processTimingBatch(JoinPoint pjp, Object returnedObject, Chronables chrons) {
        for (Chronable chron : chrons.value()) {
            this.processTiming(pjp, returnedObject, chron);
        }
    }

    @AfterThrowing(pointcut = "chronateBatch(chrons)", throwing = "exception")
    public void processTimingBatch(JoinPoint pjp, Exception exception, Chronables chrons) {

        for (Chronable chron : chrons.value()) {
            this.processTiming(pjp, exception, chron);
        }
    }

    @AfterReturning(pointcut = "chronate(chron)", returning = "returnedObject")
    public void processTiming(JoinPoint pjp, Object returnedObject, Chronable chron) {
        if (chron.exceptionClass().equals(Chronable.NO_EXCEPTION.class)) {
            this.chroneMetric(pjp, returnedObject, chron);
        }

    }

    @AfterThrowing(pointcut = "chronate(chron)", throwing = "exception")
    public void processTiming(JoinPoint pjp, Exception exception, Chronable chron) {

        if (chron.exceptionClass().isAssignableFrom(exception.getClass())) {
            this.chroneMetric(pjp, null, chron);

        }
    }

    private void chroneMetric(JoinPoint pjp, Object returnedObject, Chronable chron) {

        if (chron.value().isEmpty() && chron.valueExpression().isEmpty()) {
            throw new IllegalArgumentException("Chronable annotation must contain value expression or value");
        }

        ChronableMetric metric;

        try {
            metric = constructMetric(pjp, returnedObject, chron);
        } catch (MetricExpressionParsingException e) {

            if (logger.isDebugEnabled()) {
                logger.debug("Metric construction exception", e);
            }
            return;
        }

        if (metric.condition) {
            service.createEvent(metric.name, metric.value);
        }
    }

    // TODO: think how we could use this code in another metric related aspects
    private ChronableMetric constructMetric(JoinPoint pjp, Object returnedObject, Chronable chron)
            throws MetricExpressionParsingException {

        AspectContext context = new AspectContext(pjp.getArgs(), pjp.getTarget(), returnedObject);

        ChronableMetric chronableMetric = new ChronableMetric();

        chronableMetric.condition = this.tryParse(chron.conditionExpression(), context, boolean.class);
        if (!chronableMetric.condition) {
            return chronableMetric;
        }

        chronableMetric.name = resolver.getTargetIdentifier(chron.name(), chron.nameSuffixExpression(), context);

        if (!chron.value().isEmpty()) {
            chronableMetric.value = Integer.parseInt(chron.value());
        } else {
            chronableMetric.value = this.tryParse(chron.valueExpression(), context, Integer.class);
        }

        return chronableMetric;

    }

    private <T> T tryParse(String value, AspectContext context, Class<T> type)
            throws MetricExpressionParsingException {

        try {
            // note: hold two exception prone methods together
            Expression exp = parser.parseExpression(value);
            return exp.getValue(context, type);
        } catch (Exception e) {
            throw new MetricExpressionParsingException("Value expression parsing error, check expression: " + value, e);

        }

    }

    private static class ChronableMetric {

        public int value;
        public String name;
        public boolean condition;

    }
}
