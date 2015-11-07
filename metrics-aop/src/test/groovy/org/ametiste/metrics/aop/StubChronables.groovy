package org.ametiste.metrics.aop

import org.ametiste.metrics.annotations.Chronable
import org.ametiste.metrics.annotations.composite.Chronables

import java.lang.annotation.Annotation

/**
 * Created by atlantis on 11/7/15.
 */
class StubChronables implements Chronables {

    Chronable[] values;

    public StubChronables(Chronable ... values) {
        this.values = values;
    }

    @Override
    Chronable[] value() {
        return values
    }

    @Override
    Class<? extends Annotation> annotationType() {
        return null
    }
}
