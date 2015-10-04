package org.ametiste.metrics.boot.configuration;

import org.springframework.beans.factory.annotation.Qualifier;

import java.lang.annotation.*;

/**
 * <p>
 *     Exclude annotated element from default metrics configuration.
 * </p>
 *
 * @since 0.2.0
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Qualifier(value = "enabledAggreagtor")
// TODO : need to test its behaviour somehow
public @interface EnabledAggregator {
}
