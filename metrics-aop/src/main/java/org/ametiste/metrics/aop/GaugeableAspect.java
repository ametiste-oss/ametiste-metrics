package org.ametiste.metrics.aop;

import org.ametiste.metrics.MetricsService;
import org.ametiste.metrics.annotations.Countable;
import org.ametiste.metrics.annotations.Gaugeable;
import org.ametiste.metrics.annotations.composite.Countables;
import org.ametiste.metrics.annotations.composite.Gaugeables;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Aspect for registration of metric gauge for methods that annotated with
 * {@link Gaugeable} annotation. Increments metric with identifier, resolved from
 * {@link Gaugeable#name()} and {@link Gaugeable#nameSuffixExpression()} for value
 * {@link Gaugeable#value()}
 *
 * @author ametiste
 * @since 0.1.0
 */
@Aspect
public class GaugeableAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private IdentifierResolver resolver;
    private MetricsService service;

    public GaugeableAspect(MetricsService service, IdentifierResolver resolver) {
        if (service == null || resolver == null) {
            throw new IllegalArgumentException("MetricsService and IdentifierResolver cant be null, however one of it is null");
        }
        this.service = service;
        this.resolver = resolver;
    }

    @Pointcut(value = "@annotation(gauge)", argNames = "gauge")
    public void gaugeRequest(Gaugeable gauge) {
    }

    @Pointcut(value = "@annotation(gaugeables)", argNames = "gaugeables")
    public void gaugeRequestMultiple(Gaugeables gaugeables) {
    }

    @AfterReturning(pointcut = "gaugeRequestMultiple(gaugeables)", returning = "returnedObject")
    public void processCounting(JoinPoint jp, Object returnedObject, Gaugeables gaugeables) {
        for (Gaugeable c : gaugeables.value()) {
            this.processCounting(jp, returnedObject, c);
        }
    }

    @AfterReturning(pointcut = "gaugeRequest(gaugeable)", returning = "returnedObject",
            argNames = "jp,returnedObject,gaugeable")
    public void processCounting(JoinPoint jp, Object returnedObject, Gaugeable gaugeable) {

        AspectContext context = new AspectContext(jp.getArgs(), jp.getTarget(), returnedObject);
        try {
            String name = resolver.getTargetIdentifier(gaugeable.name(),
                    gaugeable.nameSuffixExpression(), context);

            logger.debug("Pointcut achieved, target method: " + name);
            service.gauge(name, gaugeable.value());
        } catch (MetricExpressionParsingException e) {
            logger.debug("Gaugeable achieved, but wasnt logged, wrong expression: "
                    + gaugeable.nameSuffixExpression());
        }

    }

}
