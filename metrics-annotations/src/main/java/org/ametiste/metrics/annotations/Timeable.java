package org.ametiste.metrics.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Timeable {

    /**
     * Constant part of metric instance identifier. Not required, if empty,
     * expression in {@link Timeable#nameSuffixExpression()} is used.
     */
    String name() default "";


    /**
     * Expression for changeable part of metric that depend on runtime, can be received via SpEL expression. For
     * expression target object, arguments and returned value are accessible. For further information
     * proceed to SpEL documentation.
     * Optional argument, in case of absence only {@link Timeable#name()} is used for metrics identifier
     */
    String nameSuffixExpression() default "";

    /**
     * Defines whether method execution time should be registered in exceptional case or not. Default is {@link MetricsMode#ERROR_FREE}
     * that registers metric only in normal method execution.
     * {@link Timeable#nameSuffixExpression()} should be used with caution when {@link MetricsMode#ERROR_PRONE} because
     * metric context does not include result of method execution while registers error.
     */
    MetricsMode mode() default MetricsMode.ERROR_FREE;
}
