package org.ametiste.metrics.experimental.opentsdb.example;

import org.ametiste.metrics.experimental.opentsdb.OpenTSDBConfigurationSupport;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @since
 */
@Configuration
public class UsageExampleConfiguration extends OpenTSDBConfigurationSupport {

    @Override
    protected void configureMetric() {

        addWildcard("*", "host=hlss.core-1");

        addTag("subsystem.timing", "category=foo");

        addTag(ExampleMetricInterface.SUBSYSTEM_ERRORS, "system=dsr", "category=bar");

        addDefinition(ExampleMetricInterface.SubsystemEvents.class);

    }

}
