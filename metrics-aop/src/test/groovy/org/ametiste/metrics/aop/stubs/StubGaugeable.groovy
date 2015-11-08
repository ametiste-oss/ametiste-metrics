package org.ametiste.metrics.aop.stubs

import org.ametiste.metrics.annotations.Gaugeable

import java.lang.annotation.Annotation

/**
 * Created by atlantis on 11/7/15.
 */
class StubGaugeable implements Gaugeable{

    private String name
    private String nameSuffixExpression
    private int value

    public StubGaugeable(name, nameSuffixExpression, int value) {

        this.value = value
        this.nameSuffixExpression = nameSuffixExpression
        this.name = name
    }

    @Override
    String name() {
        return name
    }

    @Override
    String nameSuffixExpression() {
        return nameSuffixExpression
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
