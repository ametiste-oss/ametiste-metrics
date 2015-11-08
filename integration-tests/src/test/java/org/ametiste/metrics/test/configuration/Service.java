package org.ametiste.metrics.test.configuration;

import org.ametiste.metrics.annotations.Timeable;

/**
 * Created by atlantis on 11/7/15.
 */
public class Service {

    @Timeable(name="time_metric")
    public void time() {

    }
}
