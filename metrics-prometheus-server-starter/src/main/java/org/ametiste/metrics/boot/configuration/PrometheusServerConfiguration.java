package org.ametiste.metrics.boot.configuration;

import io.prometheus.client.exporter.HTTPServer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
@EnableConfigurationProperties(MetricsProperties.class)
@ConditionalOnProperty(prefix = "org.ametiste.metrics.prometheus", name = "enabled", matchIfMissing = true)
public class PrometheusServerConfiguration {

    private final MetricsProperties props;

    public PrometheusServerConfiguration(MetricsProperties props) {
        this.props = props;
    }

    @Bean
    @ConditionalOnProperty(prefix = "org.ametiste.metrics.prometheus", name = "port")
    public HTTPServer prometheusServer() throws IOException {
        return new HTTPServer(props.getPrometheus().getPort());
    }
}

