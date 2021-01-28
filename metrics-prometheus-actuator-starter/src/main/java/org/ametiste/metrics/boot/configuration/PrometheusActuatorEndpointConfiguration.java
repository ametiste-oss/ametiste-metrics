package org.ametiste.metrics.boot.configuration;

import io.prometheus.client.spring.boot.EnablePrometheusEndpoint;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnablePrometheusEndpoint
@ConditionalOnProperty(prefix = "org.ametiste.metrics.prometheus", name = "enabled", matchIfMissing = true)
public class PrometheusActuatorEndpointConfiguration {
}

