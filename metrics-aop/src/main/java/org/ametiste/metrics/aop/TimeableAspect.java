package org.ametiste.metrics.aop;

import org.ametiste.metrics.MetricsService;
import org.ametiste.metrics.annotations.MetricsMode;
import org.ametiste.metrics.annotations.Timeable;
import org.ametiste.metrics.annotations.composite.Timeables;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.util.Arrays.stream;

/**
 * Aspect for registration of method execution time for methods that annotated with
 * {@link Timeable} annotation. Increments metric with identifier, resolved from
 * {@link Timeable#name()} and {@link Timeable#nameSuffixExpression()} for value
 *
 * @author ametiste
 * @since 0.1.0
 */
@Aspect
public class TimeableAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private IdentifierResolver resolver;
    private MetricsService service;

    public TimeableAspect(MetricsService service, IdentifierResolver resolver) {
        if (service == null || resolver == null) {
            throw new IllegalArgumentException("MetricsService and IdentifierResolver cant be null, however one of it is null");
        }
        this.service = service;
        this.resolver = resolver;
    }

    @Pointcut(value = "@annotation(timeable)", argNames = "timeable")
    public void countTime(Timeable timeable) {
    }

    @Pointcut(value = "@annotation(timeables)", argNames = "timeables")
    public void countTimeBatch(Timeables timeables) {
    }

    @Around("countTimeBatch(timeables)")
    public Object processTimingBatch(ProceedingJoinPoint pjp, Timeables timeables) throws Throwable {
        return process(pjp, timeables.value());
    }

    @Around("countTime(timeable)")
    public Object processTiming(ProceedingJoinPoint pjp, Timeable timeable) throws Throwable {
        return process(pjp, timeable);
    }

    public Object process(ProceedingJoinPoint pjp, Timeable... timeables) throws Throwable {

        // TODO: I guess we should generalize measurement logic, for example
        // TODO: within the metrics-measurement module
        // TODO: see MetaMetricsCounter, I have some generic solution here

        long endTime;
        Object object;

        long startTime = System.currentTimeMillis();
        try {
            object = pjp.proceed();
            endTime = System.currentTimeMillis();
        } catch (Exception e) {
            final long errorEndTime = System.currentTimeMillis();
            stream(timeables)
                  .filter(t -> t.mode().equals(MetricsMode.ERROR_PRONE))
                  .forEach(t -> {
                      AspectContext context = new AspectContext(pjp.getArgs(), pjp.getTarget());
                      saveTime(t, context, (int) (errorEndTime - startTime));
                  });

            throw e;
        }

        for (Timeable timeable : timeables) {
            AspectContext context = new AspectContext(pjp.getArgs(), pjp.getTarget(), object);
            saveTime(timeable, context, (int) (endTime - startTime));
        }

        return object;
    }

    private void saveTime(Timeable timeable, AspectContext context, int eventTime) {

        String name;
        try {
            name = resolver.getTargetIdentifier(timeable.name(), timeable.nameSuffixExpression(), context);
        } catch (MetricExpressionParsingException e) {
            logger.debug("Timeable pointcut achieved, but wasnt logged, wrong expression: "
                    + timeable.nameSuffixExpression());
            return;
        }

        service.createEvent(name, eventTime);
        logger.debug("Timeable pointcut achieved, target method: " + name);

    }

}
