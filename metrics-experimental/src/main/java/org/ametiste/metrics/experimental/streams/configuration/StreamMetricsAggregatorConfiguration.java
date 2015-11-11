package org.ametiste.metrics.experimental.streams.configuration;

import org.ametiste.metrics.MetricsAggregator;
import org.ametiste.metrics.boot.configuration.CoreAggregator;
import org.ametiste.metrics.experimental.streams.MetricsStream;
import org.ametiste.metrics.experimental.streams.StreamMetricsAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * <p>
 * This configuration allow to use {@link MetricsStream} within the 0.2.0
 * base configuration concept based on {@link MetricsAggregator}.
 * </p>
 * <p>
 * <p>
 * To use stream based configuration only just include {@code metrics-boot-starter} and
 * {@code metrics-streams-starter} and define some streams to process your metrics.
 * </p>
 * <p>
 * <p>
 * Note, if {@code metrics-default-starter} is used, some metrics duplicates may appers,
 * cos configurations may overlap, so it is not recomended to use {@code metrics-streams-starter}
 * in mix. {@code metrics-streams-starter} considered to be advenced option for insane cases and
 * should not be required for commons.
 * </p>
 *
 * @since 0.2.0
 */
@Configuration
public class StreamMetricsAggregatorConfiguration {

    @Autowired
    private List<MetricsStream> rootUpstreams;

    @Bean
    @CoreAggregator
    public StreamMetricsAggregator streamMetricsAggregator() {
        return new StreamMetricsAggregator(rootUpstreams);
    }
}
