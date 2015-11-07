package org.ametiste.metrics.aop.stubs

import org.ametiste.metrics.annotations.Countable
import org.ametiste.metrics.annotations.composite.Countables

import java.lang.annotation.Annotation

/**
 * Created by atlantis on 11/7/15.
 */
class StubCountables implements Countables{

    private Countable[] countables;

    public StubCountables(Countable ... countables) {
        this.countables = countables
    }

    @Override
    Countable[] value() {
        return countables
    }

    @Override
    Class<? extends Annotation> annotationType() {
        return null
    }
}
