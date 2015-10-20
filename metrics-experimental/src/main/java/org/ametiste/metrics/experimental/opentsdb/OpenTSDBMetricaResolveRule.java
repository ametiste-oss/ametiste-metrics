package org.ametiste.metrics.experimental.opentsdb;

import java.util.Optional;

/**
 *
 * @since
 */
public interface OpenTSDBMetricaResolveRule {

    Optional<OpenTSDBMetricaIdentifier> evalRule(String metricaIdentifier);

}
