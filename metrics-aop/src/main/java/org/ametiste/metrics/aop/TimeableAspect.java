package org.ametiste.metrics.aop;

import org.ametiste.metrics.MetricsService;
import org.ametiste.metrics.annotations.Timeable;
import org.ametiste.metrics.annotations.composite.Timeables;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

	private IdentifierResolver resolver;

	private MetricsService service;

    public TimeableAspect(MetricsService service, IdentifierResolver resolver) {
        if(service == null || resolver == null) {
            throw new IllegalArgumentException("MetricsService and IdentifierResolver cant be null, however one of it is null");
        }
        this.service = service;
        this.resolver = resolver;
    }

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Pointcut(value = "@annotation(timeable)", argNames = "timeable")
	public void countTime(Timeable timeable) {
	}

    @Deprecated
    @Pointcut(value = "@annotation(timeables)", argNames = "timeables")
    public void countTimeBatch(Timeables timeables) {
    }

    @Deprecated
    @Around("countTimeBatch(timeables)")
    public Object processTimingBatch(ProceedingJoinPoint pjp, Timeables timeables) throws Throwable {

        long startTime = System.currentTimeMillis();
        Object object = pjp.proceed();
        long endTime = System.currentTimeMillis();

        for(Timeable timeable: timeables.value()) {
            this.saveTime(timeable, pjp, object, (int)(endTime - startTime));
        }


        return object;
    }

	@Around("countTime(timeable)")
	public Object processTiming(ProceedingJoinPoint pjp, Timeable timeable) throws Throwable {

		long startTime = System.currentTimeMillis();
		Object object = pjp.proceed();
		long endTime = System.currentTimeMillis();

        this.saveTime(timeable, pjp, object, (int)(endTime - startTime));
		return object;
	}


    public void saveTime(Timeable timeable, ProceedingJoinPoint pjp, Object returned, int eventTime) {

        AspectContext context = new AspectContext(pjp.getArgs(), pjp.getTarget(), returned);
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
