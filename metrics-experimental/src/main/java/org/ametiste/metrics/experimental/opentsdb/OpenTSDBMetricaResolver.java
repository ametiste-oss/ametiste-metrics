package org.ametiste.metrics.experimental.opentsdb;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 *
 * @since
 */
public class OpenTSDBMetricaResolver {

    private final List<OpenTSDBMetricaResolveRule> rules;

    public OpenTSDBMetricaResolver(List<OpenTSDBMetricaResolveRule> rules) {
        this.rules = new ArrayList<>(rules == null ? Collections.emptyList() : rules);
    }

    public OpenTSDBMetricaIdentifier resolveMetrica(String metricaIdentifier) {
        return rules.stream().map(r -> r.evalRule(metricaIdentifier))
                .filter(Optional::isPresent)
                .findFirst()
                .get().get(); // TBD: o_O yay
    }

    public void addResolveRule(OpenTSDBMetricaResolveRule openTSDBMetricaResolveRule) {
        rules.add(openTSDBMetricaResolveRule);
    }
}
