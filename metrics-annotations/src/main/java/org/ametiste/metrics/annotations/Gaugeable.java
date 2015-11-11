package org.ametiste.metrics.annotations;

import org.ametiste.metrics.annotations.composite.Gaugeables;

import java.lang.annotation.*;

@Repeatable(value=Gaugeables.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Gaugeable {

    /**
     * Constant part of metric instance identifier. Not required, if empty,
     * expression in {@link Gaugeable#nameSuffixExpression()} is used.
     */
    String name() default "";

    /**
     * Expression for changeable part of metric that depend on runtime, can be received via SpEL expression. For
     * expression target object, arguments and returned value are accessible. For further information
     * proceed to SpEL documentation.
     * Optional argument, in case of absence only {@link Gaugeable#name()} is used for metrics identifier
     */
    String nameSuffixExpression() default "";

    /**
     * Value of gauge, default is 1
     */
    int value() default 1;

}
