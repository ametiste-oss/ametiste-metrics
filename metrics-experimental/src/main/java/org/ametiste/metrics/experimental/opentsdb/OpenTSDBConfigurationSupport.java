package org.ametiste.metrics.experimental.opentsdb;

import org.ametiste.metrics.experimental.opentsdb.annotations.Metric;
import org.ametiste.metrics.experimental.opentsdb.annotations.MetricName;
import org.ametiste.metrics.experimental.opentsdb.annotations.MetricTag;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @since
 */
public class OpenTSDBConfigurationSupport implements InitializingBean {

    @Autowired
    private OpenTSDBMetricaResolver openTSDBMetricaResolver;

    protected void configureMetric() { }

    protected void addWildcard(String wildCard, List<String> tags) {
        openTSDBMetricaResolver.addResolveRule(new WildcardResolveRule(wildCard, tags));
    }

    protected void addWildcard(String wildcard, String... tags) {
        addWildcard(wildcard, Arrays.asList(tags));
    }

    protected void addTag(String metricName, List<String> tags) {
        openTSDBMetricaResolver.addResolveRule(new PlainResolveRule(metricName, tags));
    }

    protected void addTag(String metricName, String... tags) {
        addTag(metricName, Arrays.asList(tags));
    }

    protected void addDefinition(Class<?> metricDefinition) {

        if (!metricDefinition.isAnnotationPresent(Metric.class)) {
            throw new IllegalArgumentException();
        }

        final Field metricName = Arrays.asList(metricDefinition.getDeclaredFields())
                .stream()
                .filter((f) -> f.isAnnotationPresent(MetricName.class))
                .findFirst()
                .get();

        final Field metricTag = Arrays.asList(metricDefinition.getDeclaredFields())
                .stream()
                .filter((f) -> f.isAnnotationPresent(MetricTag.class))
                .findFirst()
                .get();

        try {
            addTag((String) metricName.get(metricDefinition), (String[]) metricTag.get(metricTag));
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException(e);
        }

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        configureMetric();
    }
}
