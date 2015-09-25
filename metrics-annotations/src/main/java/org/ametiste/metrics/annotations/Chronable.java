package org.ametiste.metrics.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = ElementType.METHOD)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Chronable {

	class NO_EXCEPTION extends Exception {
    }

	/**
	 * Constant part of metric instance identifier. Not required, if empty,
	 * expression in {@link Chronable#nameSuffixExpression()} is used.
	 *
	 */
	String name() default "";

	/**
	 * Expression for changeable part of metric that depend on runtime, can be received via SpEL expression. For
	 * expression target object, arguments and returned value are accessible. For further information
	 * proceed to SpEL documentation.
	 * Optional argument, in case of absence only {@link Chronable#name()} is used for metrics identifier
	 *
	 */
	String nameSuffixExpression() default "";

	/**
	 * Optional value expression for creating event in time, is SpEL expression,
	 * should be used with caution
	 *
	 */
	String valueExpression() default "";

	/**
	 * Required value for creating event in time. If value is defined, {@link Chronable#valueExpression()}
	 * is ignored
 	 *
	 */
	String value() default "";

	/**
	 * If defined, metric is registered only if target method threw exception of defined class, in this
	 * case {@link Chronable#conditionExpression()} is ignored
	 *
	 */
	Class<? extends Exception> exceptionClass() default NO_EXCEPTION.class;

	/**
	 * Condition SpEL expression that defines whether metric is applied. Default is true (applied always),
	 *
	*/
    String conditionExpression() default "'true'";

	// TODO maybe all this name stuff needs to be extracted to superclass

}
