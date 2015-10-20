package org.ametiste.metrics.experimental.opentsdb.example;

import org.ametiste.metrics.annotations.markers.MetricsInterface;
import org.ametiste.metrics.experimental.opentsdb.annotations.Metric;
import org.ametiste.metrics.experimental.opentsdb.annotations.MetricName;
import org.ametiste.metrics.experimental.opentsdb.annotations.MetricTag;

/**
 *
 * @since
 */
@MetricsInterface
public interface ExampleMetricInterface {

    @MetricName
    String SUBSYSTEM_TIMING = "subsystem.timing";

    @MetricName
    String SUBSYSTEM_ERRORS = "subsystem.errors";

    @Metric
    interface SubsystemEvents {

        @MetricName
        String NAME = "subsystem.events";

        @MetricTag
        String[] TAGS = {"system=dsr", "category=foo"};

    }

}
