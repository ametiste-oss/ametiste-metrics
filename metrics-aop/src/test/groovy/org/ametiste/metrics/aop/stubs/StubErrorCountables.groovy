package org.ametiste.metrics.aop.stubs

import org.ametiste.metrics.annotations.ErrorCountable
import org.ametiste.metrics.annotations.composite.ErrorCountables

import java.lang.annotation.Annotation

/**
 * Created by atlantis on 11/7/15.
 */
class StubErrorCountables implements ErrorCountables{

    private ErrorCountable[] countables;

    public StubErrorCountables(ErrorCountable ... countables) {
        this.countables = countables
    }

    @Override
    Class<? extends Annotation> annotationType() {
        return null
    }

    @Override
    ErrorCountable[] value() {
        return countables
    }
}
