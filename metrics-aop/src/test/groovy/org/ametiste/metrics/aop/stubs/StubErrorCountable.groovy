package org.ametiste.metrics.aop.stubs

import org.ametiste.metrics.annotations.ErrorCountable

import java.lang.annotation.Annotation

/**
 * Created by atlantis on 11/7/15.
 */
class StubErrorCountable implements ErrorCountable{

    private String nameSuffixExpression
    private String name

    public StubErrorCountable(name, nameSuffixExpression) {

        this.name = name
        this.nameSuffixExpression = nameSuffixExpression
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
    Class<? extends Annotation> annotationType() {
        return null
    }
}
