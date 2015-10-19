package org.ametiste.metrics.boot.configuration;

import org.springframework.beans.factory.annotation.Qualifier;

import java.lang.annotation.*;

/**
 * <p>
 * Indicates object that must be included within the core metrics configuration.
 * </p>
 *
 * @since 0.2.0
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Qualifier(value = "coreIdentifierResolver")
// TODO : need to test its behaviour somehow
public @interface CoreIdentifierResolver {
}
