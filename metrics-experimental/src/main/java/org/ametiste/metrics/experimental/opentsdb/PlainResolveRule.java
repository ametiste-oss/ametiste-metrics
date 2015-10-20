package org.ametiste.metrics.experimental.opentsdb;

import java.util.List;
import java.util.Optional;

/**
 *
 * @since
 */
public class PlainResolveRule implements OpenTSDBMetricaResolveRule {

    private final String identifier;

    private final List<String> tags;

    public PlainResolveRule(String metricaName, List<String> tags) {
        this.identifier = metricaName;
        this.tags = tags;
    }

    @Override
    public Optional<OpenTSDBMetricaIdentifier> evalRule(String metricaIdentifier) {

        if (this.identifier.equals(metricaIdentifier)) {
            return Optional.of(new OpenTSDBMetricaIdentifier(metricaIdentifier, tags));
        }

        return Optional.empty();
    }

}
