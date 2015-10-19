package org.ametiste.metrics.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Countable {

    /**
     * Constant part of metric instance identifier. Not required, if empty,
     * expression in {@link Countable#nameSuffixExpression()} is used.
     */
    String name() default "";

    /**
     * Expression for changeable part of metric that depend on runtime, can be received via SpEL expression. For
     * expression target object, arguments and returned value are accessible. For further information
     * proceed to SpEL documentation.
     * Optional argument, in case of absence only {@link Countable#name()} is used for metrics identifier
     */
    String nameSuffixExpression() default "";

    /**
     * Delta of increment, default is 1
     */
    int value() default 1;

}
