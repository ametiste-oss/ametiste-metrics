package org.ametiste.metrics.aop.stubs

import org.ametiste.metrics.annotations.Timeable
import org.ametiste.metrics.annotations.composite.Timeables

import java.lang.annotation.Annotation

/**
 * Created by atlantis on 11/7/15.
 */
class StubTimeables implements Timeables{

    private Timeable[] timeables

    public StubTimeables(Timeable ... timeables) {

        this.timeables = timeables
    }

    @Override
    Timeable[] value() {
        return timeables
    }

    @Override
    Class<? extends Annotation> annotationType() {
        return null
    }
}
