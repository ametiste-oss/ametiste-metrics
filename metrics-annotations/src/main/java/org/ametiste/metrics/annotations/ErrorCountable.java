package org.ametiste.metrics.annotations;

import org.ametiste.metrics.annotations.composite.ErrorCountables;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Repeatable(value = ErrorCountables.class)
public @interface ErrorCountable {

    /**
     * Constant part of metric instance identifier. Not required, if empty,
     * expression in {@link ErrorCountable#nameSuffixExpression()} is used.
     */
    String name() default "";

    /**
     * Expression for changeable part of metric that depend on runtime, can be received via SpEL expression. For
     * expression target object and arguments are accessible. For further information
     * proceed to SpEL documentation.
     * Optional argument, in case of absence only {@link ErrorCountable#name()} is used for metrics identifier
     */
    String nameSuffixExpression() default "";

}
