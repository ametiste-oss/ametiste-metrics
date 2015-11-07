package org.ametiste.metrics.aop.stubs

import org.ametiste.metrics.annotations.Chronable

import java.lang.annotation.Annotation

/**
 * Created by atlantis on 11/7/15.
 */
class StubChronable implements Chronable {

    private String name
    private String nameExpression
    private String value
    private String valueExpression
    private String conditionExpression
    private Class<? extends Exception> exceptionClass

    public StubChronable(String name, String nameExpression, String value,
                         String valueExpression, String conditionExpression, Class<? extends Exception> exceptionClass) {

        this.exceptionClass = exceptionClass
        this.conditionExpression = conditionExpression
        this.valueExpression = valueExpression
        this.value = value
        this.nameExpression = nameExpression
        this.name = name
    }

    @Override
    String name() {
        return name
    }

    @Override
    String nameSuffixExpression() {
        return nameExpression
    }

    @Override
    String valueExpression() {
        return valueExpression
    }

    @Override
    String value() {
        return value
    }

    @Override
    Class<? extends Exception> exceptionClass() {
        return exceptionClass
    }

    @Override
    String conditionExpression() {
        return conditionExpression
    }

    @Override
    Class<? extends Annotation> annotationType() {
        return null
    }
}
