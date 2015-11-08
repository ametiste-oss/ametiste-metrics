package org.ametiste.metrics.aop.stubs

import org.ametiste.metrics.annotations.Countable

import java.lang.annotation.Annotation

/**
 * Created by atlantis on 11/7/15.
 */
class StubCountable implements Countable {

    private String name
    private String nameExpression
    private int value

    public StubCountable(name, nameExpression, int value) {

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
    int value() {
        return value
    }

    @Override
    Class<? extends Annotation> annotationType() {
        return null
    }
}
