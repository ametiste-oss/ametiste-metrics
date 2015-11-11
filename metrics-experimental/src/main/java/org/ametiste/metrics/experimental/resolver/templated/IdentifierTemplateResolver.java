package org.ametiste.metrics.experimental.resolver.templated;

import org.ametiste.metrics.resolver.MetricsIdentifierResolver;

import java.util.List;

/**
 *
 * @since
 */
public class IdentifierTemplateResolver implements MetricsIdentifierResolver {

    public static final String DEFAULT_IDENTIFIER_TEMPLATE = "{corePrefix}.{metricName}";

    private final String identifierTemplate;

    private final List<VariableBind> variableBinds;

    public IdentifierTemplateResolver(List<VariableBind> variableBinds) {
        this(DEFAULT_IDENTIFIER_TEMPLATE, variableBinds);
    }

    public IdentifierTemplateResolver(String identifierTemplate, List<VariableBind> variableBinds) {
        this.identifierTemplate = identifierTemplate;
        this.variableBinds = variableBinds;
    }

    @Override
    public String resolveMetricId(String metricName) {
        return VariableTemplate
                .eval(identifierTemplate, variableBinds)
                .bind(() -> new BindedVariable("metricName", metricName))
                .resolved();
    }

}
