package org.ametiste.metrics.aop;

import org.ametiste.metrics.MetricsService;
import org.ametiste.metrics.annotations.Countable;
import org.ametiste.metrics.annotations.composite.Countables;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Aspect for registration of metric count increment for methods that annotated with
 * {@link Countable} annotation. Increments metric with identifier, resolved from
 * {@link Countable#name()} and {@link Countable#nameSuffixExpression()} for value
 * {@link Countable#value()}
 *
 * @author ametiste
 * @since 0.1.0
 */
@Aspect
public class CountableAspect {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private IdentifierResolver resolver;
	private MetricsService service;

	public CountableAspect(MetricsService service, IdentifierResolver resolver) {
        if(service == null || resolver == null) {
            throw new IllegalArgumentException("MetricsService and IdentifierResolver cant be null, however one of it is null");
        }
		this.service = service;
		this.resolver = resolver;
	}

	@Pointcut(value = "@annotation(countable)", argNames = "countable")
	public void countRequest(Countable countable) {
	}

	@Deprecated
    @Pointcut(value = "@annotation(countables)", argNames = "countables")
    public void countRequestMultiple(Countables countables) {
    }

	@Deprecated
    @AfterReturning(pointcut = "countRequestMultiple(countables)", returning = "returnedObject")
    public void processCounting(JoinPoint jp, Object returnedObject, Countables countables) {
        for(Countable c: countables.value()) {
            this.processCounting(jp, returnedObject, c);
        }
    }

	@AfterReturning(pointcut = "countRequest(countable)", returning = "returnedObject",
			argNames = "jp,returnedObject,countable")
	public void processCounting(JoinPoint jp, Object returnedObject, Countable countable) {

		AspectContext context = new AspectContext(jp.getArgs(), jp.getTarget(), returnedObject);
		try {
			String name = resolver.getTargetIdentifier(countable.name(),
                    countable.nameSuffixExpression(), context);

			logger.debug("Pointcut achieved, target method: " + name);
		    service.increment(name, countable.value());
		} catch (MetricExpressionParsingException e) {
			logger.debug("Countable achieved, but wasnt logged, wrong expression: "
						+ countable.nameSuffixExpression());
		}

	}

}
