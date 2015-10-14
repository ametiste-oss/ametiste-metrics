package org.ametiste.metrics.boot.configuration;

import org.springframework.beans.factory.annotation.Qualifier;

import java.lang.annotation.*;

/**
 * <p>
 *     Indicates map that defines aggregation routing within the core metrics configuration.
 * </p>
 *
 * @since 0.2.0
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Qualifier(value = "coreAggregationRouting")
public @interface CoreAggreagationRouting {

}
