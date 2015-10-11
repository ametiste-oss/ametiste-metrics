package org.ametiste.metrics.boot.configuration;

import org.ametiste.metrics.statsd.client.ErrorMode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix=MetricsProperties.PROPS_PREFIX)
public class MetricsProperties {

    /**
     * Defines prefix name used to scope library properties.
     */
    public static final String PROPS_PREFIX = "org.ametiste.metrics";

    private Statsd statsd = new Statsd();

    private Jmx jmx = new Jmx();

    private String prefix="";

    public Statsd getStatsd() {
        return statsd;
    }

    public void setStatsd(Statsd statsd) {
        this.statsd = statsd;
    }

    public Jmx getJmx() {
        return jmx;
    }

    public void setJmx(Jmx jmx) {
        this.jmx = jmx;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }


    public static class Statsd {

        private String host = "localhost";
        private int port=8125;
        private ErrorMode mode = ErrorMode.MODERATE;

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }

        public ErrorMode getMode() {
            return mode;
        }

        public void setMode(ErrorMode mode) {
            this.mode = mode;
        }
    }

    public static class Jmx {

        private String domain = "org.ametiste.metrics";

        public String getDomain() {
            return domain;
        }

        public void setDomain(String domain) {
            this.domain = domain;
        }
    }
}
