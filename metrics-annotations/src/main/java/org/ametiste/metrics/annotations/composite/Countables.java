package org.ametiste.metrics.annotations.composite;


import org.ametiste.metrics.annotations.Countable;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Support of repeatable annotations for java 7. Should be removed
 */
@Deprecated
@Target(value = ElementType.METHOD)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Countables {

    Countable[] value();
}
