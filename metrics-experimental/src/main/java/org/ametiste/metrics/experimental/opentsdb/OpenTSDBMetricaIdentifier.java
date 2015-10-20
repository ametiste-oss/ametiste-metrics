package org.ametiste.metrics.experimental.opentsdb;

import java.util.List;

/**
 *
 * @since
 */
public class OpenTSDBMetricaIdentifier {

    final String id;

    final List<String> tags;

    public OpenTSDBMetricaIdentifier(String id, List<String> tags) {
        this.id = id;
        this.tags = tags;
    }

}
