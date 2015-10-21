package org.ametiste.metrics.experimental.opentsdb;

import java.util.List;
import java.util.Optional;

/**
 * @since
 */
public class WildCardResolveRuleFFF implements OpenTSDBMetricaResolveRule {

    private final String wildcard;
    private final List<String> tags;

    public WildCardResolveRuleFFF(String wildcard, List<String> tags) {
        // NOTE: mockup, only prefix wildcards, like foo.bar.* will work
        this.wildcard = wildcard.replace("*", "");
        this.tags = tags;
    }

    @Override
    public Optional<OpenTSDBMetricaIdentifier> evalRule(String metricaIdentifier) {

        // NOTE: mockup, only prefix wildcards, like foo.bar.* will work
        if (metricaIdentifier.startsWith(wildcard)) {
            return Optional.of(new OpenTSDBMetricaIdentifier(metricaIdentifier, tags));
        }

        return Optional.empty();
    }

}
