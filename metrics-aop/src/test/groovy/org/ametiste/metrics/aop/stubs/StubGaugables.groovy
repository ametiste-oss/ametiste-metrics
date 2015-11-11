package org.ametiste.metrics.aop.stubs

import org.ametiste.metrics.annotations.Gaugeable
import org.ametiste.metrics.annotations.composite.Gaugeables

import java.lang.annotation.Annotation

/**
 * Created by atlantis on 11/7/15.
 */
class StubGaugables implements Gaugeables {

    private Gaugeable[] gaugeables

    public StubGaugables(Gaugeable ... gaugeables) {
        this.gaugeables = gaugeables
    }

    @Override
    Gaugeable[] value() {
        return gaugeables
    }

    @Override
    Class<? extends Annotation> annotationType() {
        return null
    }
}
