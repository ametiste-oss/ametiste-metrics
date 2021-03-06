package org.ametiste.metrics.annotations.composite;

import org.ametiste.metrics.annotations.ErrorCountable;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = ElementType.METHOD)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface ErrorCountables {

    ErrorCountable[] value();
}
