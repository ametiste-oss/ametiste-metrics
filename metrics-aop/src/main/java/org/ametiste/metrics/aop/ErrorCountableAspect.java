package org.ametiste.metrics.aop;

import org.ametiste.metrics.MetricsService;
import org.ametiste.metrics.annotations.ErrorCountable;
import org.ametiste.metrics.annotations.composite.ErrorCountables;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Aspect for registration of metric count increment for methods that annotated with
 * {@link ErrorCountable} annotation. Increments metric with identifier, resolved from
 * {@link ErrorCountable#name()} and {@link ErrorCountable#nameSuffixExpression()} when method
 * is expecting to throw exception during execution
 *
 * @author ametiste
 * @since 0.1.0
 */
@Aspect
public class ErrorCountableAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private IdentifierResolver resolver;
    private MetricsService service;

    public ErrorCountableAspect(MetricsService service, IdentifierResolver resolver) {
        if (service == null || resolver == null) {
            throw new IllegalArgumentException("MetricsService and IdentifierResolver cant be null, however one of it is null");
        }
        this.service = service;
        this.resolver = resolver;
    }

    @Pointcut(value = "@annotation(errorCountable)", argNames = "errorCountable")
    public void errorRequest(ErrorCountable errorCountable) {
    }

    @Pointcut(value = "@annotation(errorCountables)", argNames = "errorCountables")
    public void errorRequestBatch(ErrorCountables errorCountables) {
    }

    @AfterThrowing("errorRequestBatch(errorCountables)")
    public void processCountingBatch(JoinPoint jp, ErrorCountables errorCountables) {
        for (ErrorCountable e : errorCountables.value()) {
            this.processCounting(jp, e);
        }

    }

    @AfterThrowing(value = "errorRequest(errorCountable)", argNames = "jp,errorCountable")
    public void processCounting(JoinPoint jp, ErrorCountable errorCountable) {

        AspectContext context = new AspectContext(jp.getArgs(), jp.getTarget());

        try {
            String name = resolver.getTargetIdentifier(errorCountable.name(), errorCountable.nameSuffixExpression(),
                    context);
            logger.debug("Error pointcut achieved, target method: " + name);
            service.increment(name, 1);
        } catch (MetricExpressionParsingException e) {
            logger.debug("Error pointcut achieved, but wasnt logged, wrong expression: "
                    + errorCountable.nameSuffixExpression());
        }

    }

}
