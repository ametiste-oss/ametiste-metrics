package org.ametiste.metrics.aop.stubs

import org.ametiste.metrics.annotations.MetricsMode
import org.ametiste.metrics.annotations.Timeable

import java.lang.annotation.Annotation

/**
 * Created by atlantis on 11/7/15.
 */
class StubTimeable implements Timeable {

    private String name
    private String nameSuffixExpression
    private MetricsMode mode

    public StubTimeable(name, nameSuffixExpression, MetricsMode mode) {

        this.mode = mode
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
    MetricsMode mode() {
        return mode
    }

    @Override
    Class<? extends Annotation> annotationType() {
        return null
    }
}
