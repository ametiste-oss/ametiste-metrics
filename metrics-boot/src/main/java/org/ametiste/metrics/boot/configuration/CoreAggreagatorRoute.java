package org.ametiste.metrics.boot.configuration;

import org.springframework.beans.factory.annotation.Qualifier;

import java.lang.annotation.*;

/**
 * <p>
 *     Indicates aggregation route definition bean.
 * </p>
 *
 * @since 0.2.0
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Qualifier(value = "coreAggreagatorRoute")
public @interface CoreAggreagatorRoute {

}
